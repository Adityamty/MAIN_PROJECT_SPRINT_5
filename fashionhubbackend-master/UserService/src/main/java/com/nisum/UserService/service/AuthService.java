package com.nisum.UserService.service;

import com.nisum.UserService.dao.RoleRepository;
import com.nisum.UserService.dao.UserRepository;
import com.nisum.UserService.dao.UserRoleRepository;
import com.nisum.UserService.dto.*;
import com.nisum.UserService.entity.Role;
import com.nisum.UserService.entity.User;
import com.nisum.UserService.entity.UserRole;
import com.nisum.UserService.exception.DatabaseException;
import com.nisum.UserService.exception.InvalidCredentialsException;
import com.nisum.UserService.exception.UserAlreadyExistsException;
import com.nisum.UserService.exception.UserNotFoundException;
import com.nisum.UserService.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public String signup(SignupRequest request) {
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
            }

            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPhoneNumber(request.getPhoneNumber());

            userRepository.save(user);

            // Assign default role 'customer' to new user
            Role customerRole = roleRepository.findByRoleName("customer")
                .orElseThrow(() -> new DatabaseException("Default role 'customer' not found"));
            UserRole userRole = new UserRole(user, customerRole);
            userRoleRepository.save(userRole);

            return "User registered successfully";

        } catch (UserAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Failed to register user: " + e.getMessage(), e);
        }
    }

    public UserResponse login(LoginRequest request) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

            if (userOpt.isEmpty()) {
                throw new UserNotFoundException("User not found with email: " + request.getEmail());
            }

            User user = userOpt.get();
            if (!user.getPassword().equals(request.getPassword())) {
                throw new InvalidCredentialsException("Invalid password for email: " + request.getEmail());
            }

            String fullName = user.getFirstName() + " " + user.getLastName();
            // Fix: Ensure roles are not null and convert to List<String>
            java.util.List<String> roles = user.getRoles() == null ? java.util.Collections.emptyList() :
                user.getRoles().stream().map(r -> r.getRoleName()).toList();
            // Updated to include userId in JWT token
            String token = jwtUtil.generateToken(user.getEmail(), roles, user.getUserId());
            return new UserResponse(user.getUserId(), fullName, user.getEmail(), token, roles);

        } catch (UserNotFoundException | InvalidCredentialsException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Failed to authenticate user: " + e.getMessage(), e);
        }
    }

    public UserBasicInfoResponse getUserBasicInfoById(Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String name = user.getFirstName() + " " + user.getLastName();
            return new UserBasicInfoResponse(name, user.getEmail(), user.getPhoneNumber());
        } else {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
    }

    public UserBasicInfoResponse getUserBasicInfoByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String name = user.getFirstName() + " " + user.getLastName();
            return new UserBasicInfoResponse(name, user.getEmail(), user.getPhoneNumber());
        } else {
            throw new UserNotFoundException("User not found with email: " + email);
        }
    }

    public UpdateUserRoleResponse updateUserRole(UpdateUserRoleRequest request) {
        try {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.getUserId()));
            Role role = roleRepository.findByRoleName(request.getRoleName())
                    .orElseThrow(() -> new DatabaseException("Role not found: " + request.getRoleName()));

            // Remove existing roles for the user (if only one role per user is allowed)
            userRoleRepository.deleteAll(
                userRoleRepository.findAll().stream()
                    .filter(ur -> ur.getUser().getUserId() == user.getUserId())
                    .toList()
            );

            UserRole userRole = new UserRole(user, role);
            userRoleRepository.save(userRole);

            return new UpdateUserRoleResponse(user.getUserId(), role.getRoleName(), "User role updated successfully");
        } catch (UserNotFoundException e) {
            throw e;
        } catch (DatabaseException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Failed to update user role: " + e.getMessage(), e);
        }
    }

    public UserBasicInfoResponse getUserBasicInfoByIdWithCustomerCheck(Integer userId, boolean isAuthenticated) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean isCustomer = user.getRoles().stream().anyMatch(r -> "customer".equalsIgnoreCase(r.getRoleName()));
            if (isCustomer || isAuthenticated) {
                String name = user.getFirstName() + " " + user.getLastName();
                return new UserBasicInfoResponse(name, user.getEmail(), user.getPhoneNumber());
            } else {
                throw new com.nisum.UserService.exception.JwtAuthenticationException("Forbidden: Not a customer and not authenticated");
            }
        } else {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
    }
}