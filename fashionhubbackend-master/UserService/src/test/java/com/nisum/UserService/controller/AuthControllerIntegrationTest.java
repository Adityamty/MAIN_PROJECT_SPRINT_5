package com.nisum.UserService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.UserService.dao.UserRepository;
import com.nisum.UserService.dto.LoginRequest;
import com.nisum.UserService.dto.SignupRequest;
import com.nisum.UserService.dto.UserBasicInfoResponse;
import com.nisum.UserService.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void fullJwtAuthFlow() throws Exception {
        // Clean up: delete user if exists before signup
        userRepository.findByEmail("jwtuser@example.com")
            .ifPresent(userRepository::delete);

        // 1. Signup
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName("Test");
        signupRequest.setLastName("User");
        signupRequest.setEmail("jwtuser@example.com");
        signupRequest.setPassword("password123");
        signupRequest.setPhoneNumber("1234567890");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isCreated());

        // 2. Login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("jwtuser@example.com");
        loginRequest.setPassword("password123");

        MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        String loginResponseJson = loginResult.getResponse().getContentAsString();
        UserResponse loginResponse = objectMapper.readValue(loginResponseJson, UserResponse.class);
        String jwtToken = loginResponse.getToken();
        assertThat(jwtToken).isNotBlank();

        // 3. Access protected endpoint with JWT
        MvcResult currentUserResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/current")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jwtuser@example.com"))
                .andReturn();

        String currentUserJson = currentUserResult.getResponse().getContentAsString();
        UserBasicInfoResponse userInfo = objectMapper.readValue(currentUserJson, UserBasicInfoResponse.class);
        assertThat(userInfo.getEmail()).isEqualTo("jwtuser@example.com");
    }
}
