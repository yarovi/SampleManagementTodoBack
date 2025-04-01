package org.yasmani.io.todomanagerapp.service;

import org.yasmani.io.todomanagerapp.dto.LoginDto;
import org.yasmani.io.todomanagerapp.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);
    String login(LoginDto loginDto);

}
