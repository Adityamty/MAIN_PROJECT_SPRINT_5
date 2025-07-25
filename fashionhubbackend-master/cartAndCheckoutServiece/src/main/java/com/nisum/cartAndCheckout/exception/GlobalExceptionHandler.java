package com.nisum.cartAndCheckout.exception;

import com.nisum.cartAndCheckout.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // --- USER AUTHENTICATION ---
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, "/login");
    }

    @ExceptionHandler(OrderProcessingException.class)
    public ResponseEntity<Map<String, Object>> handleOrderProcessing(OrderProcessingException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAddressNotFound(AddressNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
    }

    // --- ORDER RELATED ---
//    @ExceptionHandler(OrderProcessingException.class)
//    public ResponseEntity<Map<String, Object>> handleOrderProcessing(OrderProcessingException ex) {
//        log.warn("Order processing failed: {}", ex.getMessage());
//        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
//    }

    // --- INVENTORY RELATED ---
    @ExceptionHandler({InventoryException.class, InventoryMappingException.class})
    public ResponseEntity<Map<String, Object>> handleInventoryErrors(RuntimeException ex) {
        log.warn("Inventory service error: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
    }

    // --- PROMO SERVICE ---
    @ExceptionHandler(PromoServiceUnavailableException.class)
    public ResponseEntity<Map<String, Object>> handlePromoService(PromoServiceUnavailableException ex) {
        log.warn("Promo service unavailable: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE, null);
    }


    //Cart Page Exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidQuantity(InvalidQuantityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(UnauthorizedCartAccessException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorizedAccess(UnauthorizedCartAccessException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    // --- FALLBACK HANDLER ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericError(Exception ex) {
        log.error("Unhandled error occurred", ex);
        return buildErrorResponse("Something went wrong. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    // --- HELPER METHOD ---
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status, String redirect) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        if (redirect != null) {
            error.put("redirect", redirect);
        }
        return new ResponseEntity<>(error, status);
    }
}
