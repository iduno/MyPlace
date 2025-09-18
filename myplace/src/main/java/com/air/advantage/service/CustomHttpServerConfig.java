package com.air.advantage.service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.air.advantage.config.CommunicationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.mutiny.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * This class configures the Vert.x HTTP server at the lowest possible level
 * to be more permissive with malformed URLs and provide custom error handling.
 */
@ApplicationScoped
public class CustomHttpServerConfig {

    @Inject
    Vertx vertx;
    
    @Inject
    CommunicationConfig communicationConfig;
    
    @Inject
    WebServiceResource webServiceResource;
    
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    int defaultHttpPort;
    
    // Cache of method mappings to avoid repeated reflection lookups
    private final Map<String, Method> methodCache = new ConcurrentHashMap<>();
    
    // Configure ObjectMapper to be more lenient when deserializing
    private final ObjectMapper objectMapper = new ObjectMapper()
        .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
        .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
        .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
        .configure(com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    
    // Store HTTP server instance to manage lifecycle
    private HttpServer httpServer;
    
    /**
     * Finds a web service method for the given path and HTTP method.
     * Searches through methods in WebServiceResource annotated with @Path and @WebService.
     * 
     * @param path the request path
     * @param httpMethod the HTTP method (GET, POST, etc.)
     * @return the Method object if found, or null if not found
     */
    private Method findWebServiceMethod(String path, io.vertx.core.http.HttpMethod httpMethod) {
        // Remove leading slash if present
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        
        // Check cache first
        String cacheKey = httpMethod.name() + ":" + path;
        Method cachedMethod = methodCache.get(cacheKey);
        if (cachedMethod != null) {
            return cachedMethod;
        }
        
        // Scan all methods in WebServiceResource
        for (Method method : WebServiceResource.class.getDeclaredMethods()) {
            // Check for WebService annotation
            WebService webService = method.getAnnotation(WebService.class);
            if (webService == null) {
                continue;
            }
            
            // Check for Path annotation
            Path pathAnnotation = method.getAnnotation(Path.class);
            if (pathAnnotation == null) {
                continue;
            }
            
            // Check if HTTP method is supported
            boolean methodMatches = false;
            
            // First check if explicit methods are specified in the WebService annotation
            int methodsBitmap = webService.methods();
            if (methodsBitmap > 0) {
                // Convert Vert.x HTTP method to our bitmap value
                int requestMethodBit = 0;
                switch (httpMethod.name()) {
                    case "GET":
                        requestMethodBit = WebService.HttpMethod.GET;
                        break;
                    case "POST":
                        requestMethodBit = WebService.HttpMethod.POST;
                        break;
                    case "PUT":
                        requestMethodBit = WebService.HttpMethod.PUT;
                        break;
                    case "DELETE":
                        requestMethodBit = WebService.HttpMethod.DELETE;
                        break;
                    case "HEAD":
                        requestMethodBit = WebService.HttpMethod.HEAD;
                        break;
                    case "OPTIONS":
                        requestMethodBit = WebService.HttpMethod.OPTIONS;
                        break;
                    case "PATCH":
                        requestMethodBit = WebService.HttpMethod.PATCH;
                        break;
                }
                
                // Check if the requested method is in the bitmap
                methodMatches = (methodsBitmap & requestMethodBit) != 0;
            } else {
                // Fall back to JAX-RS annotations if no explicit methods specified
                if (httpMethod == io.vertx.core.http.HttpMethod.GET) {
                    methodMatches = method.isAnnotationPresent(GET.class);
                } else if (httpMethod == io.vertx.core.http.HttpMethod.POST) {
                    methodMatches = method.isAnnotationPresent(POST.class);
                } else if (httpMethod == io.vertx.core.http.HttpMethod.PUT) {
                    methodMatches = method.isAnnotationPresent(PUT.class);
                } else if (httpMethod == io.vertx.core.http.HttpMethod.DELETE) {
                    methodMatches = method.isAnnotationPresent(DELETE.class);
                }
            }
            
            if (!methodMatches) {
                continue;
            }
            
            // Extract the path from the annotation
            String annotationPath = pathAnnotation.value();
            if (annotationPath.startsWith("/")) {
                annotationPath = annotationPath.substring(1);
            }
            
            // Check if paths match
            if (annotationPath.equals(path)) {
                // Cache the method for future lookups
                methodCache.put(cacheKey, method);
                return method;
            }
        }
        
        return null;
    }
    
    /**
     * Invokes a web service method with the given parameters.
     * 
     * @param method the Method to invoke
     * @param jsonBody the JSON body of the request (can be null)
     * @return the Response from the method
     * @throws Exception if an error occurs during invocation
     */
    private Response invokeWebServiceMethod(Method method, String jsonBody) throws Exception {
        // Check method parameter types
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        
        // If no parameters, just invoke the method with empty args
        if (parameters.length == 0) {
            // Leave args empty
        }
        // If there's only one parameter and it's a String, pass the JSON body directly
        else if (parameters.length == 1 && parameters[0].getType() == String.class) {
            args[0] = jsonBody;
        } 
        // If there's only one parameter and we have JSON, try to parse it to match the parameter type
        else if (parameters.length == 1 && jsonBody != null && !jsonBody.isEmpty()) {
            try {
                Class<?> paramType = parameters[0].getType();
                
                // Check if the parameter is a parameterized type (like Map<String, DataAircon>)
                if (parameters[0].getParameterizedType() != null && 
                    !parameters[0].getParameterizedType().equals(paramType)) {
                    // Use TypeReference for generic types to preserve type information
                    args[0] = objectMapper.readValue(
                        jsonBody, 
                        objectMapper.getTypeFactory().constructType(parameters[0].getParameterizedType())
                    );
                } else {
                    // For simple types
                    args[0] = objectMapper.readValue(jsonBody, paramType);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("Cannot parse JSON for " + parameters[0].getType().getName() + ": " + e.getMessage(), e);
            }
        }
        // If there are multiple parameters, try to extract them from a JSON object
        else if (parameters.length > 1 && jsonBody != null && !jsonBody.isEmpty()) {
            try {
                // Parse JSON into a Map
                Map<String, Object> jsonMap = objectMapper.readValue(jsonBody, new TypeReference<Map<String, Object>>() {});
                
                // For each parameter, look for a matching entry in the map
                for (int i = 0; i < parameters.length; i++) {
                    Parameter param = parameters[i];
                    String paramName = param.getName();
                    Class<?> paramType = param.getType();
                    
                    if (jsonMap.containsKey(paramName)) {
                        Object value = jsonMap.get(paramName);
                        
                        // For primitives and Strings, use direct assignment
                        if (paramType == String.class || 
                            paramType.isPrimitive() || 
                            Number.class.isAssignableFrom(paramType) ||
                            Boolean.class == paramType) {
                            args[i] = value;
                        } 
                        // For complex types, convert via JSON
                        else {
                            // Serialize and deserialize to convert properly
                            String valueJson = objectMapper.writeValueAsString(value);
                            
                            // Check if the parameter is a parameterized type (like Map<String, DataAircon>)
                            if (param.getParameterizedType() != null && 
                                !param.getParameterizedType().equals(paramType)) {
                                // Use TypeFactory to preserve generic type information
                                args[i] = objectMapper.readValue(
                                    valueJson, 
                                    objectMapper.getTypeFactory().constructType(param.getParameterizedType())
                                );
                            } else {
                                // For simple types
                                args[i] = objectMapper.readValue(valueJson, paramType);
                            }
                        }
                    }
                    // Parameter not found in JSON, leave as null
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("Cannot parse JSON for multiple parameters: " + e.getMessage(), e);
            }
        }
        
        // Invoke the method
        try {
            return (Response) method.invoke(webServiceResource, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Access error invoking web service method: " + e.getMessage(), e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            } else {
                throw new RuntimeException("Error invoking web service method: " + e.getMessage(), e);
            }
        }
    }

    public void configServer(@Observes StartupEvent ev) {
        Integer serverPort = communicationConfig.http().serverPort();
        
        // Only start the server if a port is configured
        if (serverPort == null) {
            System.out.println("Custom HTTP server disabled - no port configured");
            return;
        }
        
        System.out.println("Configuring custom HTTP server options on port " + serverPort);

        HttpServerOptions options = new HttpServerOptions();
        options.setMaxInitialLineLength(communicationConfig.http().maxLineLength());
        options.setMaxHeaderSize(communicationConfig.http().maxHeaderSize());
        options.setDecoderInitialBufferSize(communicationConfig.http().decoderBufferSize());

        io.vertx.core.Vertx coreVertx = vertx.getDelegate();
        io.vertx.core.http.HttpClient client = coreVertx.createHttpClient();

        // Create the HTTP server with our custom options
        httpServer = coreVertx.createHttpServer(options);
        
        // Set the request handler
        httpServer.requestHandler(request -> {
            try {
                System.out.println("[RawRequestHandler] Received request: " + request.uri());

                String uri = request.uri();
                int queryIndex = uri.indexOf('?');
                String path = queryIndex != -1 ? uri.substring(0, queryIndex) : uri;
                String rawQuery = queryIndex != -1 && uri.length() > queryIndex + 1
                        ? uri.substring(queryIndex + 1)
                        : "";
                
                // First, try to find a matching web service method using reflection
                Method webServiceMethod = findWebServiceMethod(path, request.method());
                
                if (webServiceMethod != null) {
                    System.out.println("  Found matching WebService method: " + webServiceMethod.getName());
                    
                    // Special handling for GET requests with json parameter
                    String jsonBody = null;
                    if (request.method() == io.vertx.core.http.HttpMethod.GET && rawQuery.contains("json=")) {
                        // Decode the URL parameters
                        rawQuery = URLDecoder.decode(rawQuery, StandardCharsets.UTF_8);
                        String[] pairs = rawQuery.split("&");
                        
                        // Extract the json parameter value
                        for (String pair : pairs) {
                            String[] kv = pair.split("=", 2);
                            if (kv.length > 1 && kv[0].equals("json")) {
                                jsonBody = kv[1];
                                break;
                            }
                        }
                    } 
                    // For POST/PUT, get the body
                    else if (request.method() == io.vertx.core.http.HttpMethod.POST || 
                             request.method() == io.vertx.core.http.HttpMethod.PUT) {
                        request.body().onSuccess(buffer -> {
                            try {
                                // Get the body content as a string
                                String bodyContent = buffer.toString();
                                
                                // Invoke the web service method with the body content
                                Response response = invokeWebServiceMethod(webServiceMethod, bodyContent);
                                
                                // Handle the response
                                if (response != null) {
                                    // Set response status
                                    request.response().setStatusCode(response.getStatus());
                                    
                                    // Set response headers if any
                                    if (response.getHeaders() != null) {
                                        response.getHeaders().forEach((key, values) -> {
                                            for (Object value : values) {
                                                request.response().putHeader(key, value.toString());
                                            }
                                        });
                                    }
                                    
                                    // Set content type
                                    Object contentType = response.getMediaType();
                                    if (contentType != null) {
                                        request.response().putHeader("Content-Type", contentType.toString());
                                    }
                                    
                                    // Set the body
                                    Object entity = response.getEntity();
                                    if (entity != null) {
                                        try {
                                            // Properly serialize object to JSON if it's not a String
                                            if (!(entity instanceof String)) {
                                                String serialized = objectMapper.writeValueAsString(entity);
                                                request.response().end(serialized);
                                            } else {
                                                request.response().end(entity.toString());
                                            }
                                        } catch (IOException e) {
                                            System.err.println("Error serializing response: " + e.getMessage());
                                            request.response().setStatusCode(500).end("{\"error\":\"Error serializing response\"}");
                                        }
                                    } else {
                                        request.response().end();
                                    }
                                } else {
                                    // No response, send 204 No Content
                                    request.response().setStatusCode(204).end();
                                }
                            } catch (Exception e) {
                                System.err.println("Error invoking web service method: " + e.getMessage());
                                request.response().setStatusCode(500).end("{\"error\":\"" + e.getMessage() + "\"}");
                            }
                        }).onFailure(err -> {
                            System.err.println("Error reading request body: " + err.getMessage());
                            request.response().setStatusCode(400).end("{\"error\":\"Error reading request body\"}");
                        });
                        
                        // We've handled this request asynchronously
                        return;
                    }
                    
                    // For GET requests or GET with json, invoke synchronously
                    try {
                        Response response = invokeWebServiceMethod(webServiceMethod, jsonBody);
                        
                        // Handle the response
                        if (response != null) {
                            // Set response status
                            request.response().setStatusCode(response.getStatus());
                            
                            // Set response headers if any
                            if (response.getHeaders() != null) {
                                response.getHeaders().forEach((key, values) -> {
                                    for (Object value : values) {
                                        request.response().putHeader(key, value.toString());
                                    }
                                });
                            }
                            
                            // Set content type
                            Object contentType = response.getMediaType();
                            if (contentType != null) {
                                request.response().putHeader("Content-Type", contentType.toString());
                            }
                            
                            // Set the body
                            Object entity = response.getEntity();
                            if (entity != null) {
                                try {
                                    // Properly serialize object to JSON if it's not a String
                                    if (!(entity instanceof String)) {
                                        String serialized = objectMapper.writeValueAsString(entity);
                                        request.response().end(serialized);
                                    } else {
                                        request.response().end(entity.toString());
                                    }
                                } catch (IOException e) {
                                    System.err.println("Error serializing response: " + e.getMessage());
                                    request.response().setStatusCode(500).end("{\"error\":\"Error serializing response\"}");
                                }
                            } else {
                                request.response().end();
                            }
                        } else {
                            // No response, send 204 No Content
                            request.response().setStatusCode(204).end();
                        }
                        
                        // We've handled this request directly
                        return;
                    } catch (Exception e) {
                        System.err.println("Error invoking web service method: " + e.getMessage());
                        request.response().setStatusCode(500).end("{\"error\":\"" + e.getMessage() + "\"}");
                        return;
                    }
                }
                
                // If we reach here, no matching method was found, or we chose to forward
                // Process request normally with URL encoding for forwarding
                rawQuery = URLDecoder.decode(rawQuery, StandardCharsets.UTF_8);
                StringBuilder escapedQuery = new StringBuilder();
                if (!rawQuery.isEmpty()) {
                    String[] pairs = rawQuery.split("&");
                    for (int i = 0; i < pairs.length; i++) {
                        String[] kv = pairs[i].split("=", 2);
                        String key = URLEncoder.encode(kv[0], StandardCharsets.UTF_8);
                        String value = kv.length > 1 ? URLEncoder.encode(kv[1], StandardCharsets.UTF_8) : "";
                        escapedQuery.append(key).append("=").append(value);
                        if (i < pairs.length - 1) escapedQuery.append("&");
                    }
                }

                String forwardUri = path + (escapedQuery.length() > 0 ? "?" + escapedQuery : "");
                System.out.println("  Forwarding to default server: " + forwardUri);

                // Forward to default Quarkus server using configured port
                client.request(request.method(), 
                    defaultHttpPort, 
                    "localhost", 
                    forwardUri)
                    .onSuccess(clientReq -> {
                        // Copy headers from original request if needed
                        request.headers().forEach(entry -> {
                            clientReq.putHeader(entry.getKey(), entry.getValue());
                        });
                        
                        // Handle request body if present and send to internal server
                        if (request.method() == io.vertx.core.http.HttpMethod.POST || 
                            request.method() == io.vertx.core.http.HttpMethod.PUT) {
                            request.body().onSuccess(buffer -> {
                                clientReq.end(buffer);
                            });
                        } else {
                            clientReq.end();
                        }
                        
                        // Handle the response from internal server
                        clientReq.response().onSuccess(response -> {
                            // Set the response headers and status
                            request.response().setStatusCode(response.statusCode());
                            response.headers().forEach(entry -> {
                                request.response().putHeader(entry.getKey(), entry.getValue());
                            });
                            
                            // Handle the response body
                            response.body().onSuccess(body -> {
                                request.response().end(body);
                            });
                        })
                        .onFailure(err -> {
                            System.err.println("Error proxying request: " + err.getMessage());
                            request.response().setStatusCode(502).end();
                        });
                    })
                    .onFailure(err -> {
                        System.err.println("Error creating client request: " + err.getMessage());
                        request.response().setStatusCode(502).end();
                    });

                    

                } catch (Exception e) {
                    System.err.println("Error in raw request handler: " + e.getMessage());
                    request.response().setStatusCode(500).end();
                }
            })
            .listen(serverPort);

        System.out.println("Custom HTTP server listening on port " + serverPort);
    }
    
    /**
     * Shutdown handler to ensure the HTTP server is properly closed
     * when the application is shutting down.
     */
    public void onShutdown(@Observes ShutdownEvent ev) {
        if (httpServer != null) {
            System.out.println("Shutting down custom HTTP server");
            try {
                // Use a CountDownLatch to wait for server close completion
                java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1);
                
                httpServer.close(result -> {
                    if (result.succeeded()) {
                        System.out.println("Custom HTTP server closed successfully");
                    } else {
                        System.err.println("Error closing custom HTTP server: " + result.cause().getMessage());
                    }
                    latch.countDown();
                });
                
                // Wait for a maximum of 5 seconds for the server to close
                latch.await(5, java.util.concurrent.TimeUnit.SECONDS);
            } catch (Exception e) {
                System.err.println("Exception during HTTP server shutdown: " + e.getMessage());
            }
        }
    }
}
