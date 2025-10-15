package com.example.jwt.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.jwt.demo.Services.JwtService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    // Fake login endpoint
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");

        // just for demo, no DB auth yet
        if ("john".equals(username) && "12345".equals(password)) {
            String token = jwtService.generateToken(username);
            return Map.of("token", token);
        } else {
            return Map.of("error", "Invalid credentials");
        }
    }

    // Protected endpoint
    @GetMapping("/validate")
    public String validate(@RequestHeader("Authorization") String token) {
        String username = jwtService.validateToken(token.replace("Bearer ", ""));
        return "Token valid for user: " + username;
    }
}
