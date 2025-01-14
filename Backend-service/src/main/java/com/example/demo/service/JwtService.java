package com.example.demo.service;

import com.example.demo.common.TokenType;
import org.springframework.security.core.GrantedAuthority;

import java.nio.file.AccessDeniedException;
import java.util.Collection;

public interface JwtService {

    String generateAccessToken(Long userId, String userName, Collection<? extends GrantedAuthority> authorities );

    String generateRefreshToken(Long userId, String userName, Collection<? extends GrantedAuthority> authorities );

    String extractUsername(String token, TokenType type) throws AccessDeniedException;
}
