package com.sorokaandriy.springsecurityjwt.controller;

import com.sorokaandriy.springsecurityjwt.entity.MyUser;
import com.sorokaandriy.springsecurityjwt.repository.MyUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RegistrationController {

    private final MyUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(MyUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register/user")
    public MyUser createUser(@RequestBody MyUser myUser){
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        log.info("User " + myUser.getUserName() + " was registered");
        return repository.save(myUser);
    }
}
