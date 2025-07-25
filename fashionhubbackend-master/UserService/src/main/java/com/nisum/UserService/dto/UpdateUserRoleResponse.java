package com.nisum.UserService.dto;

public class UpdateUserRoleResponse {
    private Integer userId;
    private String roleName;
    private String message;

    public UpdateUserRoleResponse() {}

    public UpdateUserRoleResponse(Integer userId, String roleName, String message) {
        this.userId = userId;
        this.roleName = roleName;
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

