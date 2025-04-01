package org.yasmani.io.todomanagerapp.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yasmani.io.todomanagerapp.entity.User;
import org.yasmani.io.todomanagerapp.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {


        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + usernameOrEmail));
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.
                userdetails.
                User(user.getUsername(),
                user.getPassword(),
                authorities);
    }
}
