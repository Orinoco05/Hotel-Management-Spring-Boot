package com.example.demo.config;

import com.sendgrid.SendGrid;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppConfig {

//    @Value("${spring.sendgrid.api-key}")
//    private String sendgridApiKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Vô hiệu hóa CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Cho phép tất cả các request
                        .anyRequest().authenticated()      // Yêu cầu xác thực với các request khác
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Không duy trì session (stateless)
                );
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer ignoreResources() {
        return web -> web.ignoring()
                .requestMatchers(
                        "/actuator/**",         // Bỏ qua bảo mật cho Actuator
                        "/v3/**",               // Bỏ qua Swagger API v3
                        "/webjars/**",          // Bỏ qua các tài nguyên tĩnh
                        "/swagger-ui/**",       // Bỏ qua Swagger UI
                        "/swagger-ui/*swagger-initializer.js", // JS khởi tạo Swagger
                        "/swagger-ui.html"      // Bỏ qua file chính của Swagger UI
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SendGrid sendGrid() {
//        return new SendGrid();
//    }

}
