package com.nisum.UserService.service;

import com.nisum.UserService.dao.UserRepository;
import com.nisum.UserService.dto.UserBasicInfoResponse;
import com.nisum.UserService.entity.User;
import com.nisum.UserService.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserBasicInfoResponse getUserBasicInfoByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        String fullName = user.getFirstName() + " " + user.getLastName();
        return new UserBasicInfoResponse(fullName, user.getEmail(), user.getPhoneNumber());
    }
}
