package com.catalogservice.controller;

import com.catalogservice.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthorize_MissingToken() {
        Map<String, String> body = new HashMap<>();
        ResponseEntity<?> response = authController.authorize(body);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Token is required", response.getBody());
    }

    @Test
    void testAuthorize_InvalidToken() {
        Map<String, String> body = new HashMap<>();
        body.put("token", "invalidtoken");
        when(jwtUtil.extractRole("invalidtoken")).thenThrow(new RuntimeException("Invalid token"));
        ResponseEntity<?> response = authController.authorize(body);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid token", response.getBody());
    }

    @Test
    void testAuthorize_ValidCmsAdminToken() {
        Map<String, String> body = new HashMap<>();
        body.put("token", "validtoken");
        when(jwtUtil.extractRole("validtoken")).thenReturn("cms_admin");
        ResponseEntity<?> response = authController.authorize(body);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("authorized"));
        assertEquals(true, ((Map<?, ?>) response.getBody()).get("authorized"));
    }

    @Test
    void testAuthorize_ValidNonAdminToken() {
        Map<String, String> body = new HashMap<>();
        body.put("token", "othertoken");
        when(jwtUtil.extractRole("othertoken")).thenReturn("user");
        ResponseEntity<?> response = authController.authorize(body);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("You are not authorized to access this resource", response.getBody());
    }

    @Test
    void testAuthorize_TokenWithPrefix() {
        Map<String, String> body = new HashMap<>();
        body.put("token", "Token validtoken");
        when(jwtUtil.extractRole("validtoken")).thenReturn("cms_admin");
        ResponseEntity<?> response = authController.authorize(body);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
