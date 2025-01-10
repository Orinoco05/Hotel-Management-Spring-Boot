package com.example.demo.controller.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter

public class CustomerPageResponse  extends PageResponseAbstract implements Serializable {

    private List<CustomerResponse> customers;

}
