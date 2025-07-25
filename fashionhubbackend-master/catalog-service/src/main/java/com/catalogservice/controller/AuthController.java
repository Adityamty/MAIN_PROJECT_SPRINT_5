package com.catalogservice.controller;

import com.catalogservice.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is required");
        }
        try {
            // Remove Token prefix if present
            if (token.startsWith("Token ")) {
                token = token.substring(6).trim();
            }
            String role = jwtUtil.extractRole(token);
            if ("cms_admin".equals(role)) {
                return ResponseEntity.ok(Map.of("authorized", true));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to access this resource");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
