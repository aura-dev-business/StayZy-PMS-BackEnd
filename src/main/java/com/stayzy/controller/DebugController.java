package com.stayzy.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/debug")
public class DebugController {
    @GetMapping("/authorities")
    public String getAuthorities(Authentication authentication) {
        if (authentication == null) {
            return "No authentication found.";
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return "Authorities: " + authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));
    }
}
