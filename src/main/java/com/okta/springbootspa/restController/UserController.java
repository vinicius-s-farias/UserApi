package com.okta.springbootspa.restController;

import com.okta.springbootspa.dto.UserDto;
import com.okta.springbootspa.model.User;
import com.okta.springbootspa.model.UserOrder;
import com.okta.springbootspa.model.UserStock;
import com.okta.springbootspa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/users")
    public List<User> listar() {
        return userRepository.findAll();
    }
    @GetMapping("/users/{username}")
    public Long list(@PathVariable ("username")String user)  {
        Optional<User> us = userRepository.FindUser(user) ;
            if(us.isEmpty()){
            return userRepository.save(new User(user, "qualquercoisa", 10000)).getId();
            }else {
                return us.get().getId();
            }
    }

    @GetMapping("/teste/{id}")
    public List <User> list(@PathVariable ("id")Long user){
        return userRepository.FindUser(user);
    }

    @PostMapping("/users")
    public User adicionar(@RequestBody User user) {
        return userRepository.save(user);
    }
    @DeleteMapping("/users")
    public void deletaUser(@RequestBody User user){
        userRepository.delete(user);
    }
    @PutMapping("/users")
    public User atualizaUser(@RequestBody User user) {
        return userRepository.save(user);
    }



}
