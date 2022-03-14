package com.okta.springbootspa.dto;

import com.okta.springbootspa.keys.Chave;
import com.okta.springbootspa.model.User;
import com.okta.springbootspa.model.UserStock;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserStockDto {
    private Long id;
    private Long id_user;
    private Long id_stock;
    private String stock_symbol;
    private String stock_name;
    private Long volume;

    public UserStockDto(UserStock userStock) {
    }

    public UserStockDto() {
    }

    public UserStock transObj(User user){
        return new UserStock(
                user,
                id_user,
                id_stock,
                stock_symbol,
                stock_name,
                volume
        );
    }
}
