package com.okta.springbootspa.controller;

import com.okta.springbootspa.dto.UserOrderDto;
import com.okta.springbootspa.model.User;
import com.okta.springbootspa.model.UserOrder;
import com.okta.springbootspa.model.UserStock;
import com.okta.springbootspa.repository.UserOrderRepository;
import com.okta.springbootspa.repository.UserRepository;
import com.okta.springbootspa.repository.UserStockRepository;
import com.okta.springbootspa.service.MatchService;
import com.okta.springbootspa.service.UserStockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class UserOrderController {

    @Autowired
    private UserOrderRepository userOrderRepository;
    @Autowired
    private UserStockRepository userStockRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserStockService userStockService;
    @Autowired
    private MatchService matchService;


    @GetMapping("/orders")
    public List<UserOrder> listar() {
        return userOrderRepository.findOrder();
    }

    @PostMapping("/order")
    public ResponseEntity<UserOrder> criaOrdem(@RequestBody UserOrderDto dto ,@RequestHeader("Authorization") String token) {
        User user = userRepository.findById(dto.getIdUser()).orElseThrow();
        List<UserStock> uStock = userStockRepository.findStock(dto.getIdUser(), dto.getIdStock());
        Double dollar = user.getDollarBalance();
        Double mult = dto.getPrice() * dto.getVolume();
        if(dollar >= mult && dto.getType() == 0) {
            UserOrder userOrders = userOrderRepository.save(dto.transObj(user));
            userStockService.teste1(userOrders.getIdStock(), token);
            Double money = user.getDollarBalance() - dto.getPrice() * dto.getVolume();
            user.setDollarBalance(money);
            matchService.match();
            return new ResponseEntity<>(userOrders, HttpStatus.CREATED);
        } else if(dto.getType() == 1 &&  !uStock.isEmpty()  ){
            if(dto.getVolume() <= uStock.get(0).getVolume() ){
                UserOrder userOrders = userOrderRepository.save(dto.transObj(user));
                userStockService.teste1(userOrders.getIdStock(), token);
                Long wallet = uStock.get(0).getVolume() - dto.getVolume();
                uStock.get(0).setVolume(wallet);
                matchService.match();
                return new ResponseEntity<>(userOrders, HttpStatus.CREATED);
            }else {
                return ResponseEntity.badRequest().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }


    }

    @GetMapping("/page")
    Page<UserOrder> getStock(Pageable page){
        return userOrderRepository.findOrder2(page);
    }

}




