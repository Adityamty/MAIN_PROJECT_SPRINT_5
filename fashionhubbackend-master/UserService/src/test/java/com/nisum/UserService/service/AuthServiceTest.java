package com.nisum.UserService.service;

import com.nisum.UserService.dao.RoleRepository;
import com.nisum.UserService.dao.UserRepository;
import com.nisum.UserService.dao.UserRoleRepository;
import com.nisum.UserService.dto.LoginRequest;
import com.nisum.UserService.dto.SignupRequest;
import com.nisum.UserService.dto.UpdateUserRoleRequest;
import com.nisum.UserService.dto.UpdateUserRoleResponse;
import com.nisum.UserService.entity.Role;
import com.nisum.UserService.entity.User;
import com.nisum.UserService.entity.UserRole;
import com.nisum.UserService.exception.DatabaseException;
import com.nisum.UserService.exception.InvalidCredentialsException;
import com.nisum.UserService.exception.UserAlreadyExistsException;
import com.nisum.UserService.exception.UserNotFoundException;
import com.nisum.UserService.security.JwtUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signup_shouldThrowException_whenEmailExists() {
        SignupRequest req = new SignupRequest();
        req.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> authService.signup(req));
    }

    @Test
    void signup_shouldReturnSuccess_whenEmailIsNew() {
        SignupRequest req = new SignupRequest();
        req.setEmail("new@example.com");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPassword("1234");

        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        Role customerRole = new Role();
        customerRole.setId(1);
        customerRole.setRoleName("customer");
        when(roleRepository.findByRoleName("customer")).thenReturn(Optional.of(customerRole));
        when(userRoleRepository.save(any(UserRole.class))).thenReturn(new UserRole());

        String result = authService.signup(req);

        assertEquals("User registered successfully", result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void signup_shouldAssignCustomerRoleByDefault() {
        SignupRequest req = new SignupRequest();
        req.setEmail("customer@example.com");
        req.setFirstName("Jane");
        req.setLastName("Doe");
        req.setPassword("pass");
        req.setPhoneNumber("1234567890");

        when(userRepository.findByEmail("customer@example.com")).thenReturn(Optional.empty());
        Role customerRole = new Role();
        customerRole.setId(1);
        customerRole.setRoleName("customer");
        when(roleRepository.findByRoleName("customer")).thenReturn(Optional.of(customerRole));
        // Mock userRepository.save to return a user with userId set
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setUserId(123); // Simulate DB-generated ID
            return u;
        });

        String result = authService.signup(req);
        assertEquals("User registered successfully", result);
        verify(userRepository).save(any(User.class));
        verify(userRoleRepository).save(any(UserRole.class));
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        LoginRequest req = new LoginRequest();
        req.setEmail("missing@example.com");

        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.login(req));
    }

    @Test
    void login_shouldThrowException_whenPasswordIncorrect() {
        LoginRequest req = new LoginRequest();
        req.setEmail("abc@example.com");
        req.setPassword("wrong");

        User user = new User();
        user.setEmail("abc@example.com");
        user.setPassword("correct");

        when(userRepository.findByEmail("abc@example.com")).thenReturn(Optional.of(user));

        assertThrows(InvalidCredentialsException.class, () -> authService.login(req));
    }

    @Test
    void login_shouldReturnUserResponse_whenPasswordCorrect() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@example.com");
        req.setPassword("password");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("Test");
        user.setLastName("User");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken("test@example.com", java.util.Collections.emptyList())).thenReturn("mock-jwt-token");
        var response = authService.login(req);
        assertEquals("test@example.com", response.getEmail());
        assertEquals("Test User", response.getFullName());
        assertEquals("mock-jwt-token", response.getToken());
    }

    @Test
    void updateUserRole_shouldUpdateRoleSuccessfully() {
        User user = new User();
        user.setUserId(10);
        user.setEmail("roleupdate@example.com");
        Role newRole = new Role();
        newRole.setId(2);
        newRole.setRoleName("ims_admin");
        UpdateUserRoleRequest req = new UpdateUserRoleRequest(10, "ims_admin");

        when(userRepository.findById(10)).thenReturn(Optional.of(user));
        when(roleRepository.findByRoleName("ims_admin")).thenReturn(Optional.of(newRole));
        when(userRoleRepository.findAll()).thenReturn(Collections.emptyList());

        UpdateUserRoleResponse resp = authService.updateUserRole(req);
        assertEquals(10, resp.getUserId());
        assertEquals("ims_admin", resp.getRoleName());
        assertEquals("User role updated successfully", resp.getMessage());
        verify(userRoleRepository).save(any(UserRole.class));
    }

    @Test
    void updateUserRole_shouldThrowException_whenUserNotFound() {
        UpdateUserRoleRequest req = new UpdateUserRoleRequest(99, "ims_admin");
        when(userRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> authService.updateUserRole(req));
    }

    @Test
    void updateUserRole_shouldThrowException_whenRoleNotFound() {
        User user = new User();
        user.setUserId(11);
        UpdateUserRoleRequest req = new UpdateUserRoleRequest(11, "nonexistent_role");
        when(userRepository.findById(11)).thenReturn(Optional.of(user));
        when(roleRepository.findByRoleName("nonexistent_role")).thenReturn(Optional.empty());
        assertThrows(DatabaseException.class, () -> authService.updateUserRole(req));
    }
}
