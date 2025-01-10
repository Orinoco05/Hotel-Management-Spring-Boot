package com.example.demo.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;
@Getter

public class CustomerPasswordRequest implements Serializable {
    @NotNull(message = "ID must be not null")
    @Min(value = 1, message = "customerId must be greater than 0")
    private Long id;

    @NotBlank(message = "Password must be not blank")
    private String password;

    @NotBlank(message = "Password must be not blank")
    private String confirmPassword;

}
