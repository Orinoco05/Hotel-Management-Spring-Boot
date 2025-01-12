package com.example.demo.service.impl;

import com.example.demo.common.TokenType;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

import static com.example.demo.common.TokenType.ACCESS_TOKEN;
import static com.example.demo.common.TokenType.REFRESH_TOKEN;

@Service
@Slf4j(topic = "JWT-SERVICE")
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiryMinutes}")
    private Long expiryMinutes;

    @Value("${jwt.expiryDay}")
    private Long expiryDay;

    @Value("${jwt.accessKey}")
    private String accessKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;


    @Override
    public String generateAccessToken(Long userId, String userName, Collection<? extends GrantedAuthority> authorities) {
        log.info("Generate access token for user: {} with authorities: {}", userId, authorities);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", authorities);

        return generateToken(claims, userName);
    }

    @Override
    public String generateRefreshToken(Long userId, String userName, Collection<? extends GrantedAuthority> authorities) {
        log.info("Generate refresh token for user: {} with authorities: {}", userId, authorities);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", authorities);

        // Gọi generateRefreshToken để tạo refresh token
        return generateRefreshToken(claims, userName);
    }


    @Override
    public String extractUsername(String token, TokenType type) throws AccessDeniedException {
        log.info("Extract username from token: {} with type: {}", token, type);
        return extractClaim(token, type, Claims::getSubject);
    }



    private <T> T extractClaim(String token, TokenType type, Function<Claims, T> claimsExtractor) throws AccessDeniedException {
        final Claims claims = extraAllClaim(token, type);
        return claimsExtractor.apply(claims);
    }

    private Claims extraAllClaim(String token, TokenType type) throws AccessDeniedException {
        try {
            return Jwts.parser().setSigningKey(accessKey).parseClaimsJws(token).getBody();
        } catch (SignatureException | ExpiredJwtException e) {
            throw new AccessDeniedException("Access denied!, error:" + e.getMessage());
        }
    }

    private String generateToken(Map<String, Object> claims, String username) {
        log.info("Generate token for user: {} with claims: {}", username, claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * 60 * 60 * expiryMinutes))
                .signWith(getKey(ACCESS_TOKEN) , SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(Map<String, Object> claims, String username) {
        log.info("Generate refreshToken for user: {} with claims: {}", username, claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * expiryDay))
                .signWith(getKey(REFRESH_TOKEN) , SignatureAlgorithm.HS256)
                .compact();
    }
//
//    private Key getKey(TokenType type) {
//        switch (type) {
//            case ACCESS_TOKEN -> {
//                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
//            }
//            case REFRESH_TOKEN -> {
//                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
//            }
//            default -> throw new InvalidDataException("Invalid Token Type: " + type);
//        }
//    }
//}
private Key getKey(TokenType type) {
    switch (type) {
        case ACCESS_TOKEN:
            // Ensure the key is at least 256 bits long for secure HMAC-SHA signing
            return Keys.secretKeyFor(SignatureAlgorithm.HS256);
        case REFRESH_TOKEN:
            // Ensure the key is at least 256 bits long for secure HMAC-SHA signing
            return Keys.secretKeyFor(SignatureAlgorithm.HS256);
        default:
            throw new InvalidDataException("Invalid Token Type: " + type);
    }
}
}
