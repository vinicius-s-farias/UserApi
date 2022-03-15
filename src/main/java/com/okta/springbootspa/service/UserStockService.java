package com.okta.springbootspa.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.okta.springbootspa.configuration.TreinamentoDefaultException;
import com.okta.springbootspa.dto.StockDto;
import com.okta.springbootspa.dto.UserStockDto;
import com.okta.springbootspa.model.UserStock;
import com.okta.springbootspa.repository.UserStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController

public class UserStockService {


    @Autowired
    private UserStockRepository userStockRepository;
    @Autowired
    private WebClient webClienStock;

    @GetMapping("")
    public StockDto obterPorCodigo(Long id, @RequestHeader("Authorization") String token) {
        Mono<StockDto> monoStock = this.webClienStock
                .method(HttpMethod.GET)
                .uri("/stocks/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(StockDto.class);

        return monoStock.block();
    }



    public StockDto teste1(Long id, @RequestHeader("Authorization") String token) {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("askMin", userStockRepository.getAskMin(id));
        json.put("askMax", userStockRepository.getAskMax(id));
        json.put("bidMin", userStockRepository.getBidMin(id));
        json.put("bidMax", userStockRepository.getBidMax(id));
        Mono<StockDto> monoStock =
                this.webClienStock
                        .post()
                        .uri("stocks/teste")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .body(BodyInserters.fromValue(json))
                        .retrieve()
                        .bodyToMono(StockDto.class);
        return  monoStock.block();
    }

    public UserStockDto getWallet(Long id) throws TreinamentoDefaultException {
        UserStock userStock = userStockRepository.findById(id)
                .orElseThrow(() -> new TreinamentoDefaultException("CARTEIR_NOT_FOUND"));
        return new UserStockDto(userStock);

    }


}
