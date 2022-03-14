package com.okta.springbootspa.service;

import com.okta.springbootspa.dto.UserOrderDto;
import com.okta.springbootspa.dto.UserStockDto;
import com.okta.springbootspa.model.UserOrder;
import com.okta.springbootspa.repository.CompraRepository;
import com.okta.springbootspa.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MatchService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private CompraRepository compraRepository;

    public UserOrder match()  {
            List<UserOrder> userBuyPO = compraRepository.fyndBuyPO();
            List<UserOrder> userBuyNE = compraRepository.findBuyNE();
            List<UserOrder> userSalePO = vendaRepository.findSalePO();
            List<UserOrder> userSaleNE = vendaRepository.findSaleNE();

        if(!userSalePO.isEmpty() ){
            System.out.println("venda positiva");
            for (UserOrder cont: userSalePO) {
                vendaRepository.updateDollarBalancePO(cont.getId_user());
                vendaRepository.updateRemainingPO( cont);
                vendaRepository.atualizarBalancePO(cont.getId_user(), cont.getId_stock());
            }
            vendaRepository.updateStatus2();
        }
                if(!userBuyNE.isEmpty()) {
                    System.out.println("compra negativa");
                    for (UserOrder cont:userBuyNE)  {
                        compraRepository.updateDollarBalanceNE(cont, cont.getId_user());
                        compraRepository.insert(cont.getId_user(), cont.getId_stock(), cont.getStock_symbol(), cont.getStock_name());
                        compraRepository.atualizarBalanceNE(cont.getId_order(),cont.getId_user(), cont.getId_stock());
                        compraRepository.RemainingNE(cont);
                    }
                    vendaRepository.updateStatus2();
                }

                if(!userBuyPO.isEmpty()){
                    System.out.println("compra positiva");
                    for ( UserOrder cont: userBuyPO ) {
                        compraRepository.updateDollarBalancePO(cont.getId_user(), cont);
                        compraRepository.RemainigPO(cont, cont.getId_user());
                        compraRepository.insert(cont.getId_user(), cont.getId_stock(), cont.getStock_symbol(), cont.getStock_name());
                        compraRepository.atualizarBalancePO(cont.getId_order(), cont.getId_user(), cont.getId_stock());
                    }
                }
            if (!userSaleNE.isEmpty()){
                System.out.println("venda negativa");
                for (UserOrder cont: userSaleNE) {
                    vendaRepository.updateBallanceNE(cont.getId_user(), cont.getId_stock());
                    vendaRepository.updateDollarBalanceNE(cont, cont.getId_user());
                    vendaRepository.updateRemainingNE(cont);
                }
            }
            vendaRepository.updateStatus2();

            return null;
    }

}
