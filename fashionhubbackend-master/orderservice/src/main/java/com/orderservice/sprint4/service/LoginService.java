package com.orderservice.sprint4.service;

import com.orderservice.sprint4.dto.LoginDTO;
import com.orderservice.sprint4.dto.LoginResponseDTO;

public interface LoginService {
    public String validateLogin(LoginDTO dto);
}
