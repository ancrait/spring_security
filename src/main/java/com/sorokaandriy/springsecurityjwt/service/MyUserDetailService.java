package com.sorokaandriy.springsecurityjwt.service;

import com.sorokaandriy.springsecurityjwt.entity.MyUser;
import com.sorokaandriy.springsecurityjwt.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final MyUserRepository myUserRepository;

    @Autowired
    public MyUserDetailService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    // check users in db
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> optionalMyUser = myUserRepository.findMyUserByUserName(username);
        if (optionalMyUser.isPresent()){
            var myUser = optionalMyUser.get();
            return User.builder()
                    .username(myUser.getUserName())
                    .password(myUser.getPassword())
                    .roles(getRoles(myUser))
                    .build();
        }
        throw new UsernameNotFoundException("Invalid username " + username);
    }

    private String[] getRoles(MyUser myUser){
        if (myUser.getRole() == null){
            return new String[]{"USER"};
        }
        return myUser.getRole().split(",");
    }
}
