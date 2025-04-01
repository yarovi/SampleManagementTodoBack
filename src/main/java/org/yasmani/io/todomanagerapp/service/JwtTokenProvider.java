package org.yasmani.io.todomanagerapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt.expiration}")
    private long jwtExpirationDate;

    Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    // Generate JWT token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        logger.info("La suma es " + (now.getTime() + jwtExpirationDate));
        Date expiryDate = new Date(now.getTime() +  jwtExpirationDate);

        String token =Jwts.builder()
                .setSubject((username))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );

    }

    // Get user name from JWT token
    public String getUserNameFromJWT(String token){
        Claims claims= Jwts
                .parser().setSigningKey(key()).build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

    // Validate JWT token
    public boolean validateToken(String token){
        Jwts.parser().setSigningKey(key())
                .build()
                .parse(token);
        return true;
    }
}
