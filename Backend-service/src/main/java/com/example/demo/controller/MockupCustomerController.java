package com.example.demo.controller;

import com.example.demo.common.Gender;
import com.example.demo.controller.request.CustomerCreationRequest;
import com.example.demo.controller.request.CustomerPasswordRequest;
import com.example.demo.controller.request.CustomerUpdateRequest;
import com.example.demo.controller.response.CustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mockup/customer")
@Tag(name = "Mockup Customer Controller")
public class MockupCustomerController {
    @Operation(summary = "Get customers list", description = "API retrieve customer from db")
    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size) {
        CustomerResponse customerResponse1 = new CustomerResponse();
        customerResponse1.setId(1l);
        customerResponse1.setName("Khach Hang 1");
        customerResponse1.setAddress("108 Kim Ma");
        customerResponse1.setGender(Gender.valueOf(""));
        customerResponse1.setEmail("a@gmail.com");
        customerResponse1.setPhone("0123456789");
        customerResponse1.setCitizen_identification_card("0021387446213");
        customerResponse1.setNationality("Korean");

        CustomerResponse customerResponse2 = new CustomerResponse();
        customerResponse2.setId(2l);
        customerResponse2.setName("Khach Hang 2");
        customerResponse2.setAddress("30A Doi Can");
        customerResponse2.setGender(Gender.valueOf(""));
        customerResponse2.setEmail("b@gmail.com");
        customerResponse2.setPhone("033678900");
        customerResponse2.setCitizen_identification_card("093136153113");
        customerResponse2.setNationality("China");

        List<CustomerResponse> customerList = List.of(customerResponse1, customerResponse2);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "customer list");
        result.put("data", customerList);

        return result;
    }

    @Operation(summary = "Get customers detail", description = "API retrieve customer detail by ID")
    @GetMapping("/customersId")
    public Map<String, Object> getCustomerDetail(@PathVariable Long customerId) {

        CustomerResponse customerDetail = new CustomerResponse();
        customerDetail.setId(customerId);
        customerDetail.setName("Khach Hang 1");
        customerDetail.setAddress("108 Kim Ma");
        customerDetail.setGender(Gender.valueOf(""));
        customerDetail.setEmail("a@gmail.com");
        customerDetail.setPhone("0123456789");
        customerDetail.setCitizen_identification_card("0021387446213");
        customerDetail.setNationality("Korean");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "customer list");
        result.put("data", customerDetail);

        return result;
    }

    @Operation(summary = "Create customer ", description = "API add new customer to db")
    @PostMapping("/add")
    public Map<String, Object> createCustomer(CustomerCreationRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "customer created successfully");
        result.put("data", 3);

        return result;
    }

    @Operation(summary = "update customer ", description = "API update customer to db")
    @PutMapping("/upd")
    public Map<String, Object> updateCustomer(CustomerUpdateRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "customer updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Change password customer ", description = "API change password for customer to db")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(CustomerPasswordRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "Password updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "delete customer ", description = "API delete customer to db")
    @DeleteMapping("/del/{customerId}")
    public Map<String, Object> deleteCustomer(@PathVariable Long customerId) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "Customer deleted successfully");
        result.put("data", "");

        return result;
    }
}
