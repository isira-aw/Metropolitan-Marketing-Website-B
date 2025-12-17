package com.marketing.service;

import com.marketing.dto.AuthResponse;
import com.marketing.dto.LoginRequest;
import com.marketing.dto.RegisterRequest;
import com.marketing.entity.Admin;
import com.marketing.repository.AdminRepository;
import com.marketing.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (adminRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole("ADMIN");

        adminRepository.save(admin);

        String token = jwtUtil.generateToken(admin.getUsername());

        return new AuthResponse(token, admin.getUsername(), admin.getEmail(), "Registration successful");
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        String token = jwtUtil.generateToken(admin.getUsername());

        return new AuthResponse(token, admin.getUsername(), admin.getEmail(), "Login successful");
    }
}
