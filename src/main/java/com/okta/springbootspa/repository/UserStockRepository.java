package com.okta.springbootspa.repository;

import com.okta.springbootspa.model.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock, Long> {

    @Query(value = "select MAX(price) from users_orders where id_stock = ?1 and status = 1 and type = 1", nativeQuery = true)
    Double getAskMax(Long idStock);

    @Query(value = "select MIN(price) from users_orders where id_stock = ?1 and status = 1 and type = 1", nativeQuery = true)
    Double getAskMin(Long idStock);

    @Query(value = "select MAX(price) from users_orders where id_stock = ?1 and status = 1 and type = 0 ", nativeQuery = true)
    Double getBidMax(Long idStock);

    @Query(value = "select MIN(price) from users_orders where id_stock = ?1 and status = 1 and type = 0", nativeQuery = true)
    Double getBidMin(Long idStock);

    @Query(value =  "  select * from users_stocks_balances usb where id_user = ?1 " , nativeQuery = true)
    List<UserStock> findUser(Long user);

    @Query(value =  "  select * from users_stocks_balances usb where id_user = ?1 and id_stock = ?2 " , nativeQuery = true)
    List<UserStock> findStock(Long user, Long idStock);

}