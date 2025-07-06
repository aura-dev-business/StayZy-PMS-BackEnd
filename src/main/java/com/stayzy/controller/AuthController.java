package com.stayzy.controller;

import com.stayzy.dto.LoginRequest;
import com.stayzy.dto.UserResponse;
import com.stayzy.model.User;
import com.stayzy.repository.UserRepository;
import com.stayzy.security.JwtUtil;
import com.stayzy.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Object principal = authentication.getPrincipal();
            String email;
            String jwt;

            if (principal instanceof UserDetailsImpl userDetails) {
                email = userDetails.getUser().getEmail();
                // Use overloaded generateToken to include roles
                jwt = jwtUtil.generateToken(userDetails);
            } else if (principal instanceof User user) {
                email = user.getEmail();
                jwt = jwtUtil.generateToken(email); // fallback, but won't include roles
            } else if (principal instanceof org.springframework.security.core.userdetails.User springUser) {
                email = springUser.getUsername();
                jwt = jwtUtil.generateToken(springUser);
            } else {
                return ResponseEntity.status(500).body(Map.of("message", "Unexpected user type"));
            }

            ResponseCookie cookie = ResponseCookie.from("token", jwt)
                .httpOnly(true)
                .secure(false) // Set to true in production
                .path("/")
                .sameSite("Lax") // Use "None" and secure=true in production for cross-origin
                .maxAge(24 * 60 * 60)
                .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(Map.of("message", "Login successful"));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Something went wrong"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("token", "")
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(0)
            .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/test")
    public String test() {
        return "AuthController is working";
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@CookieValue(name = "token", required = false) String token) {
        System.out.println("JWT token from cookie: " + token);

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Missing or invalid token"));
        }

        try {
            String email = jwtUtil.extractUsername(token);
            System.out.println("Extracted email: " + email);

            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("message", "User not found"));
            }

            User user = optionalUser.get();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("fullName", user.getFullName());
            userInfo.put("email", user.getEmail());
            userInfo.put("username", user.getUsername());
            userInfo.put("totalBookings", user.getBookings().size());
            userInfo.put("totalWishlist", user.getWishlist().size());

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of("message", "Invalid or expired token"));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(403).body(Map.of("message", "Unauthorized or session expired"));
        }

        User user = userDetails.getUser();

        UserResponse response = new UserResponse(
            user.getFullName(),
            user.getEmail(),
            user.getUsername(),
            user.getBookings().size(),
            user.getWishlist().size()
        );

        return ResponseEntity.ok(response);
    }
}
