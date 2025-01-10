package com.example.demo.controller.request;

import com.example.demo.common.Gender;
import com.example.demo.common.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
@Getter
@ToString

public class CustomerUpdateRequest implements Serializable {
    @NotNull(message = "ID must be not null")
    @Min(value = 1, message = "customerId must be greater than 0")
    private Long id;
    @NotBlank(message = "Name must be not blank")
    private String name;
    private String address;
    private String phone;

    @Email(message = "Email invalid")
    private String email;
    private Status status;
    private Gender gender;
    private String nationality;
    private String citizen_identification_card;
}
