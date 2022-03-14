package com.okta.springbootspa.repository;

import com.okta.springbootspa.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
        @Repository
        public interface UserOrderRepository extends JpaRepository<UserOrder, Long > {

        @Query(value =  " select * from users_orders uo   where status = 1 order by updated_on desc fetch first 4 rows only" , nativeQuery = true)
        List<UserOrder> FindOrder();

        }









