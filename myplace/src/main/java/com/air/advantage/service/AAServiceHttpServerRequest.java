package com.air.advantage.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.StringJoiner;

import org.jboss.logging.Logger;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.impl.HttpServerRequestInternal;
import io.vertx.core.http.impl.HttpServerRequestWrapper;

/**
 * HttpServerRequest wrapper to fix the invalid URI parameters that the MyPlace application sends.
 */
public class AAServiceHttpServerRequest extends HttpServerRequestWrapper {

    private static final Logger LOGGER = Logger.getLogger(AAServiceHttpServerRequest.class);

    private final String normalizedPath;
    private final String normalizedQuery;
    private final String normalizedUri;

    public AAServiceHttpServerRequest(HttpServerRequest request) {
        this(request, null);
    }

    /**
     * Creates a normalized request delegating to {@code request} while allowing path/query values
     * to be copied from {@code copyFrom} when provided.
     */
    public AAServiceHttpServerRequest(HttpServerRequest request, HttpServerRequest copyFrom) {
        super(asInternalRequest(request));

        HttpServerRequest source = copyFrom != null ? copyFrom : request;
        this.normalizedPath = source.path();
        this.normalizedQuery = normalizeQuery(source.params());
        this.normalizedUri = normalizedQuery.isEmpty()
                ? normalizedPath
                : normalizedPath + "?" + normalizedQuery;

        LOGGER.debugf("Normalized request: path=%s, query=%s, uri=%s", normalizedPath, normalizedQuery, normalizedUri);
    }

    public AAServiceHttpServerRequest(AAServiceHttpServerRequest source) {
        this(source, source);
    }

    @Override
    public String uri() {
        return normalizedUri;
    }

    @Override
    public String path() {
        return normalizedPath;
    }

    @Override
    public String query() {
        return normalizedQuery;
    }

    private static HttpServerRequestInternal asInternalRequest(HttpServerRequest request) {
        if (request instanceof HttpServerRequestInternal internal) {
            return internal;
        }
        throw new IllegalArgumentException("HttpServerRequest must be an internal Vert.x implementation");
    }

    private static String normalizeQuery(MultiMap queryParams) {
        if (queryParams == null || queryParams.isEmpty()) {
            return "";
        }

        StringJoiner queryStringBuilder = new StringJoiner("&");
        queryParams.forEach(entry -> {
            String key = entry.getKey();
            List<String> values = queryParams.getAll(key);
            for (String value : values) {
                String encodedValue = value == null
                        ? ""
                        : URLEncoder.encode(value, StandardCharsets.UTF_8);
                String encodedKey = URLEncoder.encode(key, StandardCharsets.UTF_8);
                queryStringBuilder.add(encodedKey + "=" + encodedValue);
            }
        });
        return queryStringBuilder.toString();
    }
}