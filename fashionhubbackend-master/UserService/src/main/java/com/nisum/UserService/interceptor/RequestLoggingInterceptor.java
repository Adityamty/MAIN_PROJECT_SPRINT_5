package com.nisum.UserService.interceptor;

import com.nisum.UserService.util.LoggingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Request logging interceptor for tracking API calls and performance
 */
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    private static final String START_TIME = "startTime";
    private static final String REQUEST_ID = "requestId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = UUID.randomUUID().toString();
        String userId = extractUserId(request);

        // Set request context for tracking
        LoggingUtil.setRequestContext(requestId, userId);

        // Store timing information
        request.setAttribute(START_TIME, System.currentTimeMillis());
        request.setAttribute(REQUEST_ID, requestId);

        // Log incoming request
        logger.info("REQUEST_START - {} {} | RequestId: {} | UserId: {}",
                   request.getMethod(), request.getRequestURI(), requestId, userId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                               Object handler, Exception ex) throws Exception {
        try {
            Long startTime = (Long) request.getAttribute(START_TIME);
            String requestId = (String) request.getAttribute(REQUEST_ID);

            if (startTime != null) {
                long duration = System.currentTimeMillis() - startTime;
                String userId = extractUserId(request);

                // Log API call completion
                LoggingUtil.logApiCall(request.getMethod(), request.getRequestURI(), userId, duration);

                // Log any exceptions
                if (ex != null) {
                    LoggingUtil.logTechnicalError("REQUEST_PROCESSING",
                                                 "Request processing failed: " + request.getRequestURI(), ex);
                }

                logger.info("REQUEST_END - {} {} | Status: {} | Duration: {}ms",
                           request.getMethod(), request.getRequestURI(), response.getStatus(), duration);
            }
        } finally {
            // Always clear context
            LoggingUtil.clearRequestContext();
        }
    }

    private String extractUserId(HttpServletRequest request) {
        // Extract user ID from JWT token or session
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // TODO: Extract user ID from JWT token
            return "extracted-user-id";
        }
        return "anonymous";
    }
}
