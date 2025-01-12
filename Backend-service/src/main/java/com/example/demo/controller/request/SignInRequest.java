package com.example.demo.controller.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SignInRequest implements Serializable {
    private String email;
    private String password;
    private String platform;
    private String deviceToken;
    private String versionApp;
}
