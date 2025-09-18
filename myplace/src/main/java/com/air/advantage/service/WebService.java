package com.air.advantage.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods that can be called via the custom web service handler.
 * This allows for dynamic discovery and invocation of web service endpoints.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebService {
    /**
     * The name of the web service endpoint.
     * This should match the path part of the URL after the base path.
     */
    String value() default "";
    
    /**
     * Supported HTTP methods as a bitmap.
     * Use the constants in WebService.HttpMethod for easier specification.
     * 
     * For example:
     * - GET only: methods=HttpMethod.GET
     * - GET and POST: methods=HttpMethod.GET | HttpMethod.POST
     * - All methods: methods=HttpMethod.ALL
     * 
     * Default is to determine from JAX-RS annotations.
     */
    int methods() default 0;
    
    /**
     * HTTP method constants for use with the methods attribute.
     */
    public static final class HttpMethod {
        public static final int NONE = 0;
        public static final int GET = 1;
        public static final int POST = 2;
        public static final int PUT = 4;
        public static final int DELETE = 8;
        public static final int HEAD = 16;
        public static final int OPTIONS = 32;
        public static final int PATCH = 64;
        public static final int ALL = GET | POST | PUT | DELETE | HEAD | OPTIONS | PATCH;
    }
}
