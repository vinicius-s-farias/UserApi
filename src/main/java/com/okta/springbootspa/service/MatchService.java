package com.okta.springbootspa.service;

import com.okta.springbootspa.model.UserOrder;
import com.okta.springbootspa.repository.CompraRepository;
import com.okta.springbootspa.repository.VendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MatchService {
    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);
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
            logger.error("venda positiva");
            for (UserOrder cont: userSalePO) {
                vendaRepository.updateDollarBalancePO(cont.getIdUser());
                vendaRepository.updateRemainingPO( cont);
                vendaRepository.atualizarBalancePO(cont.getIdUser(), cont.getIdStock());
            }
            vendaRepository.updateStatus2();
        }
                if(!userBuyNE.isEmpty()) {
                    logger.error("compra negativa");
                    for (UserOrder cont:userBuyNE)  {
                        compraRepository.updateDollarBalanceNE(cont, cont.getIdUser());
                        compraRepository.insert(cont.getIdUser(), cont.getIdStock(), cont.getStockSymbol(), cont.getStockName());
                        compraRepository.atualizarBalanceNE(cont.getIdOrder(),cont.getIdUser(), cont.getIdStock());
                        compraRepository.remainingNE(cont);
                    }
                    vendaRepository.updateStatus2();
                }

                if(!userBuyPO.isEmpty()){
                    logger.error("compra positiva");
                    for ( UserOrder cont: userBuyPO ) {
                        compraRepository.updateDollarBalancePO(cont.getIdUser(), cont);
                        compraRepository.remainigPO(cont, cont.getIdUser());
                        compraRepository.insert(cont.getIdUser(), cont.getIdStock(), cont.getStockSymbol(), cont.getStockName());
                        compraRepository.atualizarBalancePO(cont.getIdOrder(), cont.getIdUser(), cont.getIdStock());
                    }
                }
            if (!userSaleNE.isEmpty()){
                logger.error("venda negativa");
                for (UserOrder cont: userSaleNE) {
                    vendaRepository.updateBallanceNE(cont.getIdUser(), cont.getIdStock());
                    vendaRepository.updateDollarBalanceNE(cont, cont.getIdUser());
                    vendaRepository.updateRemainingNE(cont);
                }
            }
            vendaRepository.updateStatus2();

            return null;
    }

}
