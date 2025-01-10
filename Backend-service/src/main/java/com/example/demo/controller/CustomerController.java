package com.example.demo.controller;

import com.example.demo.common.Gender;
import com.example.demo.controller.request.CustomerCreationRequest;
import com.example.demo.controller.request.CustomerPasswordRequest;
import com.example.demo.controller.request.CustomerUpdateRequest;
import com.example.demo.controller.response.CustomerPageResponse;
import com.example.demo.controller.response.CustomerResponse;
import com.example.demo.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@Tag(name = "Customer Controller")
@RequiredArgsConstructor
@Slf4j(topic = "CUSTOMER-CONTROLLER")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Get customers list", description = "API retrieve customer from db")
    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String sort,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size) {
        log.info("Get customers list");

        customerService.findAll(keyword, sort, page, size);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "customer list");
        result.put("data", customerService.findAll(keyword, sort, page, size));
        return result;
    }

    @Operation(summary = "Get customers detail", description = "API retrieve customer detail by ID")
    @GetMapping("/{customersId}")
    public Map<String, Object> getCustomerDetail(@PathVariable("customersId") @Min(value = 1, message = "customerId must be greater than 0") Long customerId) {
        log.info("Get customer detail by ID: {}", customerId);

       CustomerResponse customerDetail = customerService.findById(customerId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "customer list");
        result.put("data", customerDetail);

        return result;
    }

    @Operation(summary = "Create customer ", description = "API add new customer to db")
    @PostMapping("/add")
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid CustomerCreationRequest request) {
        log.info("Create customer: {}", request);


        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "customer created successfully");
        result.put("data", customerService.save(request));



        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "update customer ", description = "API update customer to db")
    @PutMapping("/upd")
    public Map<String, Object> updateCustomer(@RequestBody @Valid CustomerUpdateRequest request) {
        log.info("Updating customer : {}", request);

        customerService.update(request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "customer updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Change password customer ", description = "API change password for customer to db")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(@RequestBody @Valid CustomerPasswordRequest request) {
        log.info("Changing password customer : {}", request);

        customerService.changePassword(request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "Password updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "delete customer ", description = "API delete customer to db")
    @DeleteMapping("/del/{customerId}")
    public Map<String, Object> deleteCustomer(@PathVariable @Min(value = 1, message = "customerId must be greater than 0") Long customerId) {
        log.info("Deleting customer : {}", customerId);

        customerService.delete(customerId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "Customer deleted successfully");
        result.put("data", "");

        return result;
    }
}
