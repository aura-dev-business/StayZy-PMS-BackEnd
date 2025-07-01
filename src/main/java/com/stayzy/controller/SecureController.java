package com.stayzy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure")
public class SecureController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("✅ You are authorized, welcome to StayZy!");
    }

    @GetMapping("/me")
    public ResponseEntity<String> whoAmI(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok("Token received Suui: " + authHeader);
    }
}
