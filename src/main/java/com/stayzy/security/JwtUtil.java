package com.stayzy.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String secret = "this_is_my_super_secure_secret_key_1234567890";
    private final long expirationMs = 86400000; // 1 day
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    public static final String TOKEN_PREFIX = "Bearer ";

    // ✅ Generate token with subject (email)
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    // Overloaded: Generate token with UserDetails and roles claim
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    // ✅ Extract email/username from token
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // ✅ Validate token signature and expiration
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            System.out.println("JWT validation failed: " + e.getMessage()); // Optional for dev
            return false;
        }
    }
}
