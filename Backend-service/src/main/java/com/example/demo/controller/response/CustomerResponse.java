package com.example.demo.controller.response;

import com.example.demo.common.Gender;
import com.example.demo.common.Status;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Gender gender;
    private Status status;
    private String nationality;
    private String citizen_identification_card;
}
