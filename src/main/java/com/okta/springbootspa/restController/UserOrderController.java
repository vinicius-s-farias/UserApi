package com.okta.springbootspa.restController;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
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
        return userOrderRepository.FindOrder();
    }

    @PostMapping("/order")
    public ResponseEntity<UserOrder> criaOrdem(@RequestBody UserOrderDto dto ,@RequestHeader("Authorization") String token) {
        User user = userRepository.findById(dto.getId_user()).orElseThrow();
        List<UserStock> teste = userStockRepository.FindStock(dto.getId_user(), dto.getId_stock());
        Double dollar = user.getDollar_balance();
        Double mult = dto.getPrice() * dto.getVolume();
        if(dollar >= mult && dto.getType() == 0) {
            UserOrder userOrders = userOrderRepository.save(dto.transObj(user));
            userStockService.teste1(userOrders.getId_stock(), token);
            matchService.match();
            return new ResponseEntity<>(userOrders, HttpStatus.CREATED);
        } else if(dto.getType() == 1 &&  !teste.isEmpty()  ){
            if(dto.getVolume() <= teste.get(0).getVolume() ){
                UserOrder userOrders = userOrderRepository.save(dto.transObj(user));
                userStockService.teste1(userOrders.getId_stock(), token);
                matchService.match();
                return new ResponseEntity<>(userOrders, HttpStatus.CREATED);
            }else {
                System.out.println("Ordem não criada");
            }
        }else {
            System.out.println("Ordem não criada");
        }
        return null;
    }

}




