package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BackendServiceApplication {
//	public static void main(String[] args) {
//		SpringApplication.run(BackendServiceApplication.class, args);
//			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//			String encodedPassword = encoder.encode("123456");
//			System.out.println("Encoded Password: " + encodedPassword);
//		}
public static void main(String[] args) {
	SpringApplication.run(BackendServiceApplication.class, args);
}
	}




