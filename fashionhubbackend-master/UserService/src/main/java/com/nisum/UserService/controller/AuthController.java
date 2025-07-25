package com.nisum.UserService.controller;

import com.nisum.UserService.dto.*;
import com.nisum.UserService.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthController {

    /**
     * AuthController handles user authentication requests.
     * It provides endpoints for user signup, login, and fetching basic user information.
     */

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest request) {
        String response = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest request) {
        UserResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current")
    public ResponseEntity<UserBasicInfoResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = authentication.getName();
        UserBasicInfoResponse response = authService.getUserBasicInfoByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/basic-info/{id}")
    public ResponseEntity<UserBasicInfoResponse> getUserBasicInfo(@PathVariable("id") Integer id) {
        // Check if the user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal());
        UserBasicInfoResponse response = authService.getUserBasicInfoByIdWithCustomerCheck(id, isAuthenticated);
        return ResponseEntity.ok(response);
    }
}