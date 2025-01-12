package com.example.demo.service.impl;

import com.example.demo.controller.response.SignInRequest;
import com.example.demo.controller.response.TokenResponse;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public TokenResponse getAccessToken(SignInRequest request) {
        log.info("get access token");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String accessToken =  jwtService.generateAccessToken(1l, request.getEmail(), null);
        String refreshToken =  jwtService.generateRefreshToken(1l, request.getEmail(), null);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse getRefreshToken(String request) {
        return null;
    }

    }

