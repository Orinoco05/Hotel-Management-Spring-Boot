package com.example.demo.service;

import com.example.demo.controller.response.SignInRequest;
import com.example.demo.controller.response.TokenResponse;

public interface AuthenticationService {

    TokenResponse getAccessToken(SignInRequest request);

    TokenResponse getRefreshToken(String request);
}
