package com.nisum.UserService.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Centralized logging utility for User Service
 * Provides structured logging with SLF4J and MDC support
 */
public class LoggingUtil {

    private static final Logger logger = LoggerFactory.getLogger(LoggingUtil.class);

    // MDC Keys for structured logging
    public static final String USER_ID = "userId";
    public static final String REQUEST_ID = "requestId";
    public static final String OPERATION = "operation";
    public static final String SERVICE_NAME = "serviceName";

    static {
        MDC.put(SERVICE_NAME, "user-service");
    }

    /**
     * Log successful operations
     */
    public static void logSuccess(String operation, String message, Object... args) {
        MDC.put(OPERATION, operation);
        logger.info("SUCCESS - " + message, args);
        MDC.remove(OPERATION);
    }

    /**
     * Log business errors (expected exceptions)
     */
    public static void logBusinessError(String operation, String message, Object... args) {
        MDC.put(OPERATION, operation);
        logger.warn("BUSINESS_ERROR - " + message, args);
        MDC.remove(OPERATION);
    }

    /**
     * Log technical errors (unexpected exceptions)
     */
    public static void logTechnicalError(String operation, String message, Throwable throwable) {
        MDC.put(OPERATION, operation);
        logger.error("TECHNICAL_ERROR - " + message, throwable);
        MDC.remove(OPERATION);
    }

    /**
     * Log authentication/security events
     */
    public static void logSecurityEvent(String event, String userId, String message, Object... args) {
        MDC.put(USER_ID, userId);
        MDC.put(OPERATION, "SECURITY");
        logger.info("SECURITY_EVENT [{}] - " + message, event, args);
        MDC.remove(USER_ID);
        MDC.remove(OPERATION);
    }

    /**
     * Log API request/response
     */
    public static void logApiCall(String method, String endpoint, String userId, long duration) {
        MDC.put(USER_ID, userId);
        MDC.put(OPERATION, "API_CALL");
        logger.info("API_CALL [{} {}] - Duration: {}ms", method, endpoint, duration);
        MDC.remove(USER_ID);
        MDC.remove(OPERATION);
    }

    /**
     * Set request context for tracking
     */
    public static void setRequestContext(String requestId, String userId) {
        MDC.put(REQUEST_ID, requestId);
        if (userId != null) {
            MDC.put(USER_ID, userId);
        }
    }

    /**
     * Clear request context
     */
    public static void clearRequestContext() {
        MDC.remove(REQUEST_ID);
        MDC.remove(USER_ID);
        MDC.remove(OPERATION);
    }
}
