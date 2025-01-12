package com.example.demo.config;

import com.example.demo.service.impl.CustomerDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

//    @Value("${spring.sendgrid.api-key}")
//    private String sendgridApiKey;

    private final CustomizeRequestFilter requestFilter;
    private final CustomerDetailService customerDetailService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Vô hiệu hóa CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Cho phép tất cả các request
                        .anyRequest().authenticated()      // Yêu cầu xác thực với các request khác
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Không duy trì session (stateless)
                        .disable().authenticationProvider(authenticationProvider()).addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
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
                        "/swagger-ui.html" ,     // Bỏ qua file chính của Swagger UI
                        "/favicon.ico"
                );
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Áp dụng cho tất cả các đường dẫn
                        .allowedOrigins("*") // Cho phép tất cả các nguồn
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Các phương thức HTTP được phép
                        .allowedHeaders("*") // Tất cả các header được phép
                        .allowCredentials(false) // Không cho phép gửi cookie
                        .maxAge(3600); // Thời gian cache preflight request (3600 giây)
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(customerDetailService.CustomerDetailService());
        return authProvider;
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
