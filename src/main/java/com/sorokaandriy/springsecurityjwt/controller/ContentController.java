package com.sorokaandriy.springsecurityjwt.controller;

import com.sorokaandriy.springsecurityjwt.service.MyUserDetailService;
import com.sorokaandriy.springsecurityjwt.webtocken.JwtService;
import com.sorokaandriy.springsecurityjwt.webtocken.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ContentController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MyUserDetailService myUserDetailService;

    @Autowired
    public ContentController(AuthenticationManager authenticationManager, JwtService jwtService, MyUserDetailService myUserDetailService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.myUserDetailService = myUserDetailService;
    }

    @GetMapping("/home")
    public String handleWelcome(){
        log.info("Return home page");
        return "Welcome to home page";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome(){
        log.info("Return admin page");
        return "Welcome to admin page";
    }


    @GetMapping("/user/home")
    public String handleUserHome(){
        log.info("Return user page");
        return "Welcome to user page";
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm){
        log.info("Called authentification");
        // authenticationManager check login & password using provider & userDetailsService
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(),loginForm.password()
        ));
        if (authentication.isAuthenticated()){
            log.info("User " + loginForm.username() + " is authenticated and return token for user");
            return jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
        }
        log.error("Not found user");
        throw new UsernameNotFoundException("Invalid credentials");
    }
}
