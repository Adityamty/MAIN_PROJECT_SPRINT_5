package com.nisum.UserService.exception;

public enum ErrorCode {
    USER_ALREADY_EXISTS("USER_001", "User already exists"),
    USER_NOT_FOUND("USER_002", "User not found"),
    INVALID_CREDENTIALS("USER_003", "Invalid credentials provided"),
    VALIDATION_ERROR("USER_004", "Validation error"),
    DATABASE_ERROR("USER_005", "Database operation failed"),
    GENERIC_ERROR("USER_006", "An unexpected error occurred");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}
