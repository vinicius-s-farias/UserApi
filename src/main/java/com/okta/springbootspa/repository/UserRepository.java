package com.okta.springbootspa.repository;

import com.okta.springbootspa.dto.UserDto;
import com.okta.springbootspa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value =  " select * from users s where upper(username) = upper(?1) " , nativeQuery = true)
    Optional<User> findUser(String username);

    @Query(value =  "  select * from users usb where id = ?1 " , nativeQuery = true)
    List<User> findUser(Long user);


}
