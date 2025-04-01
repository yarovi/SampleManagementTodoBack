package org.yasmani.io.todomanagerapp.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yasmani.io.todomanagerapp.dto.LoginDto;
import org.yasmani.io.todomanagerapp.dto.RegisterDto;
import org.yasmani.io.todomanagerapp.entity.Role;
import org.yasmani.io.todomanagerapp.entity.User;
import org.yasmani.io.todomanagerapp.exception.TodoApiException;
import org.yasmani.io.todomanagerapp.repository.RoleRepository;
import org.yasmani.io.todomanagerapp.repository.UserRepository;
import org.yasmani.io.todomanagerapp.service.AuthService;
import org.yasmani.io.todomanagerapp.service.JwtTokenProvider;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {


        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST,
                    "Username is already taken");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TodoApiException(HttpStatus.BAD_REQUEST,
                    "Email is already taken");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRoleName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);


        return "The user has been registered successfully";
    }

    @Override
    public String login(LoginDto loginDto) {


        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUserNameOrEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        //return "User logged-in successfully";
        return token;

    }


}
