package com.sorokaandriy.springsecurityjwt.repository;

import com.sorokaandriy.springsecurityjwt.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser,Long> {
    Optional<MyUser> findMyUserByUserName(String userName);
}
