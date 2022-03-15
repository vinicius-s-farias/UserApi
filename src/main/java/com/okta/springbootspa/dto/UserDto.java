package com.okta.springbootspa.dto;

import com.okta.springbootspa.model.User;
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

    public User transObj(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDollarBalance(dollarBalance);
        return user;
    }
}
