package com.nisum.UserService.dto;

public class UpdateUserRoleRequest {
    private Integer userId;
    private String roleName;

    public UpdateUserRoleRequest() {}

    public UpdateUserRoleRequest(Integer userId, String roleName) {
        this.userId = userId;
        this.roleName = roleName;
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
}

