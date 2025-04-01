package org.yasmani.io.todomanagerapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmani.io.todomanagerapp.dto.JwtAuthResponse;
import org.yasmani.io.todomanagerapp.dto.LoginDto;
import org.yasmani.io.todomanagerapp.dto.RegisterDto;
import org.yasmani.io.todomanagerapp.service.AuthService;

@CrossOrigin( "*")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    //public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
