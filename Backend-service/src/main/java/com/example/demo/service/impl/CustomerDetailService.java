package com.example.demo.service.impl;

import com.example.demo.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public record CustomerDetailService(CustomerRepository customerRepository) {

    public UserDetailsService CustomerDetailService() {
        return customerRepository::findByEmail;
    }
}
