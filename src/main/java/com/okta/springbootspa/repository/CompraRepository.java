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
public interface CompraRepository extends JpaRepository<UserOrder, Long > {

    @Modifying
    @Query(value = "update users set dollar_balance = dollar_balance - ( select uo.remaining_value  * uo.price FROM users_orders a, users_orders uo " +
            " inner join users u on uo.id_user = u.id " +
            " where a.status = 1 and a.type = 0 and a.status = uo.status  and a.id_user = ?1 and a.id_user <> uo.id_user and a.id_order = ?2 fetch first 1 rows only ) where id = ?1", nativeQuery = true)
    int updateDollarBalancePO(User user, UserOrder id_order);

    @Modifying
    @Query(value = " update users set dollar_balance = dollar_balance  - ( " +
            " select a.remaining_value  * uo.price " +
            " fROM users_orders a, users_orders uo " +
            " where  a.id_stock = uo.id_stock and a.type = 0  and uo.id_order <> a.id_order and a.id_order = ?1 and a.id_user = ?2 and a.remaining_value  <> 0 fetch first 1 rows only ) where id = ?2", nativeQuery = true)
    int updateDollarBalanceNE(UserOrder id_order, User user);

    @Modifying
    @Query(value = "update users_stocks_balances set volume = volume + (select a.remaining_value " +
            " AS ID FROM users_orders a, users_orders uo " +
            " inner join users u on uo.id_user = u.id  " +
            " inner join users_stocks_balances usb on u.id = usb.id_user " +
            "  WHERE a.type <> uo.type  and a.id_stock = uo.id_stock and uo.id_order= ?1 and a.id_stock = uo.id_stock fetch first 1 rows only) where id_user = ?2 and id_stock = ?3", nativeQuery = true)
    int atualizarBalancePO(Long id_order, User id_user, Long id_stock);

    @Modifying
    @Query(value = "update users_stocks_balances set volume = volume +  (select uo.remaining_value " +
            " AS ID FROM users_orders a, users_orders uo  " +
            " inner join users u on uo.id_user = u.id  " +
            " inner join users_stocks_balances usb on u.id = usb.id_user " +
            " WHERE a.type <> uo.type  and a.id_stock = uo.id_stock and uo.id_order= ?1 fetch first 1 rows only) where id_user = ?2 and id_stock = ?3", nativeQuery = true)
    int atualizarBalanceNE(Long id_order, User id_user, Long id_stock);

    @Query(value = " select * from " +
            "                users_orders a, users_orders b where a.type <> b.type and a.remaining_value >= b.volume and a.type = 0 and b.type = 1 and a.id_stock = b.id_stock and a.id_order <> b.id_order and a.price >= b.price and a.status = 1 and b.status = 1 and a.remaining_value <> 0  order by a.created_on asc " +
            "                ", nativeQuery = true)
    List<UserOrder> fyndBuyPO();

    @Query(value = "  select * from " +
            "  users_orders a, users_orders b where a.type <> b.type and  a.remaining_value < b.volume and a.type = 0 and a.status = 1 and b.status = 1 and a.id_stock = b.id_stock and a.id_order <> b.id_order and a.price >= b.price order by a.created_on asc ", nativeQuery = true)
    List<UserOrder> findBuyNE();

    @Modifying
    @Query(value = " update users_orders  set remaining_value = ( select  uo.remaining_value  - a.volume  from " +
            " users_orders a, users_orders uo inner join users u on uo.id_user = u.id " +
            " where a.type <> uo.type and a.id_stock = uo.id_stock  and uo.id_order = ?1 and uo.id_user = ?2 order by a.created_on asc fetch first 1 rows only) where id_order=?1 and type = 0", nativeQuery = true)
    int RemainigPO(UserOrder id_order, User user );

    @Modifying
    @Query(value = " update users_orders  set remaining_value = 0 where  type = 0 and id_order = ?1", nativeQuery = true)
    int RemainingNE(UserOrder id_order);

    @Modifying
    @Query(value = " INSERT INTO users_stocks_balances (id_user, id_stock, stock_symbol, stock_name, volume) select ?1, ?2, ?3, ?4, 0 where not exists (select 1 from users_stocks_balances where id_stock = ?2 and id_user = ?1) ", nativeQuery = true)
    int insert(User user, Long id_stock, String stock_symbol, String stock_name);

}