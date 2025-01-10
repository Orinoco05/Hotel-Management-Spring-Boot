package com.example.demo.service;

import com.example.demo.controller.request.CustomerCreationRequest;
import com.example.demo.controller.request.CustomerPasswordRequest;
import com.example.demo.controller.request.CustomerUpdateRequest;
import com.example.demo.controller.response.CustomerPageResponse;
import com.example.demo.controller.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerPageResponse findAll(String keyword, String sort, int page, int size);

    CustomerResponse findById(Long id);

    CustomerResponse findByEmail(String email);

    CustomerResponse findByUsername(String username);

    long save(CustomerCreationRequest req);

    void update(CustomerUpdateRequest req);

    void delete(Long id);

    void changePassword(CustomerPasswordRequest req);
}
