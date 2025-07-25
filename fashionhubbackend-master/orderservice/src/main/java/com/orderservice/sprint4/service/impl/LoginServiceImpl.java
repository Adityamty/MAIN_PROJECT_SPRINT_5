package com.orderservice.sprint4.service.impl;

import com.orderservice.sprint4.dto.LoginDTO;
import com.orderservice.sprint4.dto.LoginResponseDTO;
import com.orderservice.sprint4.exception.ExternalServiceException;
import com.orderservice.sprint4.exception.InvalidLoginException;
import com.orderservice.sprint4.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${login.service.login.validation.url}")
    private String LOGIN_SERVICE_LOGIN_VALIDATION_URL;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String validateLogin(LoginDTO dto) {
        try {
            ResponseEntity<LoginResponseDTO> response = restTemplate.postForEntity(
                    LOGIN_SERVICE_LOGIN_VALIDATION_URL, dto, LoginResponseDTO.class);

            if (response.getStatusCode().isError()) {
                throw new InvalidLoginException("Login service returned error: " + response.getStatusCode());
            }

            LoginResponseDTO body = response.getBody();

            if (body == null || body.getToken() == null || body.getToken().isBlank()) {
                throw new InvalidLoginException("Login failed: token not found");
            }

            return body.getToken();

        } catch (HttpClientErrorException e) {
            throw new InvalidLoginException("Invalid credentials or bad request: " + e.getMessage());
        } catch (HttpServerErrorException e) {
            throw new ExternalServiceException("Login service failed: " + e.getMessage());
        } catch (ResourceAccessException e) {
            throw new ExternalServiceException("Unable to reach login service: " + e.getMessage());
        } catch (Exception e) {
            throw new ExternalServiceException("Unexpected error during login: " + e.getMessage());
        }
    }
}
