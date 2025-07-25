package com.nisum.productmanagement.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Centralized logging utility for Product Management Service
 * Provides structured logging with SLF4J and MDC support
 */
public class LoggingUtil {

    private static final Logger logger = LoggerFactory.getLogger(LoggingUtil.class);

    // MDC Keys for structured logging
    public static final String PRODUCT_ID = "productId";
    public static final String CATEGORY_ID = "categoryId";
    public static final String REQUEST_ID = "requestId";
    public static final String OPERATION = "operation";
    public static final String SERVICE_NAME = "serviceName";

    static {
        MDC.put(SERVICE_NAME, "product-management-service");
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
     * Log product operations
     */
    public static void logProductOperation(String operation, Long productId, String message, Object... args) {
        MDC.put(PRODUCT_ID, String.valueOf(productId));
        MDC.put(OPERATION, operation);
        logger.info("PRODUCT_OP - " + message, args);
        MDC.remove(PRODUCT_ID);
        MDC.remove(OPERATION);
    }

    /**
     * Log category operations
     */
    public static void logCategoryOperation(String operation, Long categoryId, String message, Object... args) {
        MDC.put(CATEGORY_ID, String.valueOf(categoryId));
        MDC.put(OPERATION, operation);
        logger.info("CATEGORY_OP - " + message, args);
        MDC.remove(CATEGORY_ID);
        MDC.remove(OPERATION);
    }

    /**
     * Log database operations with performance metrics
     */
    public static void logDatabaseOperation(String operation, String query, long duration) {
        MDC.put(OPERATION, "DATABASE");
        logger.debug("DB_OP [{}] - Query: {} | Duration: {}ms", operation, query, duration);
        MDC.remove(OPERATION);
    }

    /**
     * Set request context for tracking
     */
    public static void setRequestContext(String requestId) {
        MDC.put(REQUEST_ID, requestId);
    }

    /**
     * Clear request context
     */
    public static void clearRequestContext() {
        MDC.remove(REQUEST_ID);
        MDC.remove(PRODUCT_ID);
        MDC.remove(CATEGORY_ID);
        MDC.remove(OPERATION);
    }
}
