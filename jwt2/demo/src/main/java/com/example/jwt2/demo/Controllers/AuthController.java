package com.example.jwt2.demo.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.jwt2.demo.Entities.User;
import com.example.jwt2.demo.Repositories.UserRepository;
import com.example.jwt2.demo.Services.CustomUserDetailsService;
import com.example.jwt2.demo.Utilities.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
            CustomUserDetailsService userDetailsService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");

        // Check if user already exists
        if (!userRepository.findByUsername(username).isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(password);

        // Save user in DB
        User newUser = new User(username, encodedPassword, "USER");
        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "User created successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");

        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Generate JWT token
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtUtil.validateToken(token.replace("Bearer ", ""));
            return ResponseEntity.ok(Map.of("message", "Token valid", "username", username));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid or expired token"));
        }
    }
}
