package com.okta.springbootspa.dto;

import com.okta.springbootspa.model.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockDto {

    private Long id;
    private Double ask_min;
    private Double ask_max;
    private Double bid_min;
    private Double bid_max;


    public StockDto transObjStock(User user){
        return new StockDto(
                id,
                 ask_min,
                 ask_max,
                 bid_min,
                 bid_max
        );
    }

    public StockDto(Long id, Double ask_min, Double ask_max, Double bid_min, Double bid_max) {
        this.id = id;
        this.ask_min = ask_min;
        this.ask_max = ask_max;
        this.bid_min = bid_min;
        this.bid_max = bid_max;
    }

}
