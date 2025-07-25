package com.nisum.cartAndCheckout.validation;

import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.exception.UserNotFoundException;
import com.nisum.cartAndCheckout.security.JwtUtil;
import static com.nisum.cartAndCheckout.constants.AppConstants.USER_NOT_LOGGED_IN;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import static com.nisum.cartAndCheckout.constants.AppConstants.USER_ADDRESS_NOT_FOUND;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserValidator {

    @Autowired
    private JwtUtil jwtUtil;

    public static UserAddress validateUserAddress(Optional<UserAddress> optionalAddress) {
        return optionalAddress.orElseThrow(() -> new UserNotFoundException(USER_ADDRESS_NOT_FOUND));
    }

    public static Integer getValidatedUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");

        if (userId == null) {
            throw new UserNotFoundException(USER_NOT_LOGGED_IN);
        }

        return (Integer) userId;
    }

    // New method to get userId from JWT token instead of session
    public Integer getUserIdFromToken(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (token != null && jwtUtil.validateJwtToken(token)) {
            return jwtUtil.getUserIdFromToken(token);
        }
        throw new UserNotFoundException(USER_NOT_LOGGED_IN);
    }

    // Helper method to extract token from request
    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}