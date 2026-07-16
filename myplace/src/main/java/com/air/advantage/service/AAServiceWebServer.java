package com.air.advantage.service;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.jboss.logging.Logger;

import com.air.advantage.config.MyPlaceConfig;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.vertx.http.runtime.VertxHttpRecorder;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
public class AAServiceWebServer {
    private static final Logger LOGGER = Logger.getLogger(AAServiceWebServer.class);

    @Inject
    Vertx vertx;

    @Inject
    MyPlaceConfig config;

    private final Set<String> webServiceResourcePaths = discoverResourcePaths(WebServiceResource.class);

    public void configServer(@Observes StartupEvent ev) {
        Integer serverPort = config.communication().http().serverPort().orElse(null);
        
        // Only start the server if a port is configured
        if (serverPort == null) {
            LOGGER.info("Custom HTTP server disabled - no port configured");
            return;
        }

        String serverHost = config.communication().http().serverHost().orElse("0.0.0.0");
        
        LOGGER.infof("Configuring custom HTTP server options on http://%s:%d", serverHost, serverPort);

        HttpServerOptions options = new HttpServerOptions();
        options.setPort(serverPort);
        options.setHost(serverHost);
        options.setMaxInitialLineLength(config.communication().http().maxLineLength());
        options.setMaxHeaderSize(config.communication().http().maxHeaderSize());
        options.setDecoderInitialBufferSize(config.communication().http().decoderBufferSize());

        // Create the HTTP server with our custom options
        vertx.createHttpServer(options)
                .requestHandler(request -> {
                    String requestPath = normalizePath(request.path());
                    if (!webServiceResourcePaths.contains(requestPath)) {
                        request.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", MediaType.TEXT_PLAIN)
                                .end("Advantage Air v" + config.system().myAppRev());
                        return;
                    }

                    // Hand off a request wrapper that exposes a normalized URI to Quarkus REST core
                    VertxHttpRecorder.getRootHandler().handle(new AAServiceHttpServerRequest(request));
                }) 
                .listen(result -> {
                    if (result.succeeded()) {
                        LOGGER.infof("Relaxed-URI Secondary Listener started on http://%s:%d", serverHost, serverPort);
                    } else {
                        LOGGER.error("Failed to start relaxed-URI secondary server listener", result.cause());
                    }
                });
    }

    private static Set<String> discoverResourcePaths(Class<?> resourceClass) {
        Set<String> paths = new HashSet<>();

        Path classPathAnnotation = resourceClass.getAnnotation(Path.class);
        String classPath = classPathAnnotation != null ? classPathAnnotation.value() : "";

        for (Method method : resourceClass.getDeclaredMethods()) {
            Path methodPathAnnotation = method.getAnnotation(Path.class);
            if (methodPathAnnotation == null) {
                continue;
            }

            String combinedPath = joinPaths(classPath, methodPathAnnotation.value());
            paths.add(normalizePath(combinedPath));
        }

        return paths;
    }

    private static String joinPaths(String basePath, String subPath) {
        String base = basePath == null ? "" : basePath;
        String sub = subPath == null ? "" : subPath;

        if (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        if (!sub.startsWith("/")) {
            sub = "/" + sub;
        }
        return base + sub;
    }

    private static String normalizePath(String path) {
        if (path == null || path.isBlank()) {
            return "/";
        }
        String normalized = path.startsWith("/") ? path : "/" + path;
        if (normalized.length() > 1 && normalized.endsWith("/")) {
            return normalized.substring(0, normalized.length() - 1);
        }
        return normalized;
    }
}
