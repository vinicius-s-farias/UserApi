package com.okta.springbootspa.dto;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private double dollarBalance;

    public void user(String username , String password, Double dollarBalance) {
        this.username = username;
        this.password = password;
        this.dollarBalance = dollarBalance;
    }
}
