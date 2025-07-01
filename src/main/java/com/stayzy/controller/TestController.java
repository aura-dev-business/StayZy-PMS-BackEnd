package com.stayzy.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/secure")
    public String securePage() {
        return "You accessed a secure page!";
    }
}
