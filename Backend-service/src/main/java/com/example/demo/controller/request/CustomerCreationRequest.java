package com.example.demo.controller.request;

import com.example.demo.common.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
public class CustomerCreationRequest implements Serializable {
    @NotBlank(message = "Name must be not blank")
    private String name;
    private String address;
    private String phone;

    @Email(message = "Email invalid")
    private String email;
    private Gender gender;
    private String nationality;
    private String citizen_identification_card;
}
