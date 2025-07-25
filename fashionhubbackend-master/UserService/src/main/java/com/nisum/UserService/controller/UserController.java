package com.nisum.UserService.controller;

import com.nisum.UserService.dto.UserBasicInfoResponse;
import com.nisum.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nisum.UserService.exception.ErrorResponse;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        // Extract roles from authorities
        Collection<?> authorities = authentication.getAuthorities();
        boolean isCustomer = authorities.stream().anyMatch(a -> a.toString().equals("ROLE_customer"));
        if (!isCustomer) {
            ErrorResponse error = new ErrorResponse(
                "USER_007",
                "Forbidden",
                "User does not have 'customer' role",
                "/user/me"
            );
            error.setError("User does not have 'customer' role");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
        String email = authentication.getName();
        UserBasicInfoResponse userInfo = userService.getUserBasicInfoByEmail(email);
        return ResponseEntity.ok(userInfo);
    }
}
