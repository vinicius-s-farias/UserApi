package com.okta.springbootspa.dto;

import com.okta.springbootspa.model.User;
import com.okta.springbootspa.model.UserOrder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserOrderDto {
    private Long id_order;
    private Long id_user;
    private Long id_stock;
    private String stock_symbol;
    private String stock_name;
    private Long volume;
    private double price;
    private int type;
    private int status;
    private Long remaining_value;

    public UserOrder transObj(User user){
        return new UserOrder(
                user,
                id_user,
                id_stock,
                stock_symbol,
                stock_name,
                price,
                type,
                status,
                volume,
                remaining_value
        );
    }
}

