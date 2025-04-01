package org.yasmani.io.todomanagerapp.conf;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.yasmani.io.todomanagerapp.security.JwtAuthenticationEntryPoint;
import org.yasmani.io.todomanagerapp.security.JwtAuthenticationFilter;
import org.yasmani.io.todomanagerapp.service.JwtTokenProvider;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class TodoSecurityConfig {

    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    private JwtAuthenticationFilter authenticationFilter;

    private UserDetailsService userDetailsService;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/api/v1**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .anyRequest().authenticated()
                ).formLogin(AbstractHttpConfigurer::disable);

        http
                .exceptionHandling(exception-> exception.
                        authenticationEntryPoint(authenticationEntryPoint)

                );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

// se desabilita porque ya estamos inyectando el userDetailsService

/*
@Bean
public UserDetailsService userDetailsService() {

    UserDetails user = User.withUsername("user")
            .password(TodoSecurityConfig.passwordEncoder().encode("password"))
            .roles("USER")
            .build();

    UserDetails admin = User.withUsername("admin")
            .password(TodoSecurityConfig.passwordEncoder().encode("password"))
            .roles("ADMIN")
            .build();
    return new InMemoryUserDetailsManager(
            user, admin
    );
}

 */
}