package com.sorokaandriy.springsecurityjwt.controller;

import com.sorokaandriy.springsecurityjwt.service.MyUserDetailService;
import com.sorokaandriy.springsecurityjwt.webtocken.JwtService;
import com.sorokaandriy.springsecurityjwt.webtocken.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return "Welcome to home page";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome(){
        return "Welcome to admin page";
    }


    @GetMapping("/user/home")
    public String handleUserHome(){
        return "Welcome to user page";
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm){
        // authenticationManager check login & password using provider & userDetailsService
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(),loginForm.password()
        ));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
        }
        throw new UsernameNotFoundException("Invalid credentials");
    }
}
