package com.okta.springbootspa.dto;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private double dollar_balance;

    public void User(String username , String password, Double dollar_balance) {
        this.username = username;
        this.password = password;
        this.dollar_balance = dollar_balance;
    }
}
