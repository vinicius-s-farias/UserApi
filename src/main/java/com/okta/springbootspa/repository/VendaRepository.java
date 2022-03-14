package com.okta.springbootspa.repository;
import com.okta.springbootspa.model.User;
import com.okta.springbootspa.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface VendaRepository extends JpaRepository<UserOrder, Long> {

        @Query(value = "SELECT * FROM users_orders WHERE type = 1 and id_stock = ?1 and status = 1", nativeQuery = true)
        List<UserOrder> findMatches(Long id_stock);//procurar ordens de venda abertas

        @Query(value = "select * from " +
                " users_orders a, users_orders b where a.remaining_value < b.remaining_value and  a.type = 1 and a.id_stock = b.id_stock and a.id_order <> b.id_order  and a.status = 1 and b.status = 1  and a.type <> b.type and a.price <= b.price order by a.created_on asc", nativeQuery = true)
        List<UserOrder> findSaleNE();

        @Query(value = "SELECT * FROM users_orders a, users_orders b  where a.type <> b.type  and a.remaining_value >= b.remaining_value and a.type = 1  and a.id_stock = b.id_stock and a.status = b.status and a.status = 1 and b.status = 1  and a.price <= b.price order by a.created_on asc ", nativeQuery = true)
        List<UserOrder> findSalePO();//Pegando matches

        @Modifying
        @Query(value = "update users_orders  set remaining_value = 0 WHERE remaining_value < 0", nativeQuery = true)
        int updateStatus();

        @Modifying
        @Query(value = "UPDATE users_orders SET status = 2 WHERE remaining_value = 0", nativeQuery = true)
        int updateStatus2();


        @Modifying
        @Query(value = "update users set dollar_balance = ( select a.remaining_value * uo.price + u.dollar_balance " +
                " FROM users_orders a, users_orders uo " +
                " inner join users u on uo.id_user = u.id " +
                " where uo.status = 1  and a.id_stock = uo.id_stock and uo.type = 1 and uo.id_order <> a.id_order and uo.id_user = ?1 fetch first 1 rows only) where id = ?1", nativeQuery = true)
        int updateDollarBalancePO(User user);

        @Modifying
        @Query(value = "update users set dollar_balance =  dollar_balance +( " +
                "select a.remaining_value  * a.price " +
                "fROM users_orders a, users_orders uo " +
                "where  a.id_stock = uo.id_stock and a.type = 1  and uo.id_order <> a.id_order and a.id_order = ?1  and a.type <> uo.type " +
                ") where id = ?2", nativeQuery = true)
        int updateDollarBalanceNE(UserOrder id_order, User user);

        @Modifying
        @Query(value = "update users_orders  set remaining_value = (SELECT  a.remaining_value - b.remaining_value  AS ID FROM users_orders b, users_orders a  WHERE a.type = 1 and b.status = 1 and a.type <> b.type  and a.id_stock = b.id_stock and a.id_order = ?1 fetch first 1 rows only ) where type = 1 AND id_order = ?1", nativeQuery = true)
        int updateRemainingPO( UserOrder id_order);//Ele atualiza remaining value quando há match

        @Modifying
        @Query(value = "update users_orders  set remaining_value = 0 where id_order=?1 and type = 1 ", nativeQuery = true)
        int updateRemainingNE(UserOrder id_order);//Ele atualiza remaining value quando há match

        @Modifying
        @Query(value = "update users_stocks_balances set volume = ( " +
                " select  usb.volume - a.remaining_value " +
                " AS ID FROM users_orders a " +
                " Inner join users u on a.id_user = u.id " +
                " inner join users_stocks_balances usb on u.id = usb.id_user " +
                "  WHERE a.id_user = ?1 and a.id_stock = ?2 and usb.id_stock = ?2" +
                " ) where id_user = ?1 and id_stock = ?2", nativeQuery = true)
        int updateBallanceNE(User id_user, Long id_stock);

        @Modifying
        @Query(value = "update users_stocks_balances set volume = volume - ( select  uo.volume - uo.remaining_value " +
                "  AS ID FROM users_orders a, users_orders uo " +
                "  Inner join users u on id_user = u.id " +
                "  inner join users_stocks_balances usb on u.id = usb.id_user " +
                "  WHERE  a.id_stock = usb.id_stock and a.id_user = ?1 and a.id_stock = ?2 and uo.type = 0 fetch first 1 rows only ) where id_user = ?1 and id_stock = ?2 ", nativeQuery = true)
        int atualizarBalancePO(User user, Long id_stock);

}