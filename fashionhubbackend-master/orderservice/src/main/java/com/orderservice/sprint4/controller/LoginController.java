package com.orderservice.sprint4.controller;

import com.orderservice.sprint4.dto.LoginDTO;
import com.orderservice.sprint4.exception.ExternalServiceException;
import com.orderservice.sprint4.exception.InvalidLoginException;
import com.orderservice.sprint4.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<?> validateAndGenerateLogin(@RequestBody LoginDTO dto){
        try {
            if (dto == null || dto.getEmail() == null || dto.getPassword() == null) {
                return ResponseEntity.badRequest().body("Username or password must not be null");
            }

            String token = loginService.validateLogin(dto);
            return ResponseEntity.ok().body(token);

        } catch (InvalidLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (ExternalServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
}
