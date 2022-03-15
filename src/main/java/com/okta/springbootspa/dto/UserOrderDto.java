package com.okta.springbootspa.dto;

import com.okta.springbootspa.model.User;
import com.okta.springbootspa.model.UserOrder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserOrderDto {
    private Long idOrder;
    private Long idUser;
    private Long idStock;
    private String stockSymbol;
    private String stockName;
    private Long volume;
    private double price;
    private int type;
    private int status;
    private Long remainingValue;

    public UserOrder transObj(User user){
        UserOrder order = new UserOrder();
        order.setIdUser(user);
        order.setIdStock(idStock);
        order.setStockSymbol(stockSymbol);
        order.setStockName(stockName);
        order.setPrice(price);
        order.setType(type);
        order.setStatus(1);
        order.setRemainingValue(remainingValue);
        order.setVolume(volume);
        return order;
    }
}

