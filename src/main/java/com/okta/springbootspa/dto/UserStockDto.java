package com.okta.springbootspa.dto;

import com.okta.springbootspa.model.User;
import com.okta.springbootspa.model.UserStock;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserStockDto {
    private Long id;
    private Long idUser;
    private Long idStock;
    private String stockSymbol;
    private String stockName;
    private Long volume;

    public UserStockDto(UserStock userStock) {
    }

    public UserStockDto() {
    }

    public UserStock transObj(User user){
        return new UserStock(
                user,
                idStock,
                stockSymbol,
                stockName,
                volume
        );
    }
}
