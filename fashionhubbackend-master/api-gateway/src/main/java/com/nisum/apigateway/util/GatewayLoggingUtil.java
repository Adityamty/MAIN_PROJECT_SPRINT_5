package com.nisum.apigateway.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Centralized logging utility for API Gateway
 * Provides structured logging for gateway operations
 */
public class GatewayLoggingUtil {

    private static final Logger logger = LoggerFactory.getLogger(GatewayLoggingUtil.class);

    // MDC Keys for structured logging
    public static final String REQUEST_ID = "requestId";
    public static final String SERVICE_NAME = "serviceName";
    public static final String TARGET_SERVICE = "targetService";
    public static final String REQUEST_PATH = "requestPath";
    public static final String HTTP_METHOD = "httpMethod";

    static {
        MDC.put(SERVICE_NAME, "api-gateway");
    }

    /**
     * Log gateway routing decisions
     */
    public static void logRouting(String method, String path, String targetService, String targetUri) {
        MDC.put(HTTP_METHOD, method);
        MDC.put(REQUEST_PATH, path);
        MDC.put(TARGET_SERVICE, targetService);
        logger.info("ROUTING - {} {} -> {} ({})", method, path, targetService, targetUri);
        clearRoutingContext();
    }

    /**
     * Log request processing with timing
     */
    public static void logRequest(String method, String path, String requestId, long duration) {
        MDC.put(REQUEST_ID, requestId);
        MDC.put(HTTP_METHOD, method);
        MDC.put(REQUEST_PATH, path);
        logger.info("REQUEST_PROCESSED - {} {} | Duration: {}ms", method, path, duration);
        clearRequestContext();
    }

    /**
     * Log errors in gateway processing
     */
    public static void logGatewayError(String operation, String message, Throwable throwable) {
        logger.error("GATEWAY_ERROR [{}] - {}", operation, message, throwable);
    }

    /**
     * Log service discovery events
     */
    public static void logServiceDiscovery(String event, String serviceName, String message) {
        MDC.put(TARGET_SERVICE, serviceName);
        logger.info("SERVICE_DISCOVERY [{}] - {}", event, message);
        MDC.remove(TARGET_SERVICE);
    }

    private static void clearRoutingContext() {
        MDC.remove(HTTP_METHOD);
        MDC.remove(REQUEST_PATH);
        MDC.remove(TARGET_SERVICE);
    }

    private static void clearRequestContext() {
        MDC.remove(REQUEST_ID);
        MDC.remove(HTTP_METHOD);
        MDC.remove(REQUEST_PATH);
    }
}
