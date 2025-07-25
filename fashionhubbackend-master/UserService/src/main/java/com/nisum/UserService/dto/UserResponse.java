package com.nisum.UserService.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {
    private int userId;
    private String fullName;
    private String email;
    private String token;
    private java.util.List<String> roles;

    // Default constructor (required by Jackson)
    public UserResponse() {}

    // Parameterized constructor with Jackson annotations
    @JsonCreator
    public UserResponse(
            @JsonProperty("userId") int userId,
            @JsonProperty("fullName") String fullName,
            @JsonProperty("email") String email,
            @JsonProperty("token") String token,
            @JsonProperty("roles") java.util.List<String> roles) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.token = token;
        this.roles = roles;
    }

    // Getters (required for JSON serialization)
    public int getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
    public java.util.List<String> getRoles() { return roles; }

    // Setters (required for JSON deserialization)
    public void setUserId(int userId) { this.userId = userId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setToken(String token) { this.token = token; }
    public void setRoles(java.util.List<String> roles) { this.roles = roles; }
}
