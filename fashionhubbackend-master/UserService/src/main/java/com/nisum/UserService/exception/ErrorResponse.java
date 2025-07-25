package com.nisum.UserService.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String errorCode;
    private String message;
    private String details;
    private String path;
    private String error; // for test compatibility

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private List<ValidationError> validationErrors;

    // Default constructor
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor without validation errors
    public ErrorResponse(String errorCode, String message, String details, String path) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with validation errors
    public ErrorResponse(String errorCode, String message, String details, String path, List<ValidationError> validationErrors) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
        this.path = path;
        this.validationErrors = validationErrors;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public List<ValidationError> getValidationErrors() { return validationErrors; }
    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public String getError() { return error != null ? error : message; }
    public void setError(String error) { this.error = error; }

    // Inner class for validation errors
    public static class ValidationError {
        private String field;
        private String message;
        private Object rejectedValue;

        // Default constructor
        public ValidationError() {}

        // Constructor with parameters - THIS FIXES THE COMPILATION ERROR
        public ValidationError(String field, String message, Object rejectedValue) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
        }

        // Getters and Setters
        public String getField() { return field; }
        public void setField(String field) { this.field = field; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public Object getRejectedValue() { return rejectedValue; }
        public void setRejectedValue(Object rejectedValue) { this.rejectedValue = rejectedValue; }
    }
}
