package com.example.web.app.service;

import com.example.web.app.dao.DbSqlite;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {
    private final DbSqlite dbSqlite;

    public LoginService(DbSqlite dbSqlite) {
        this.dbSqlite = dbSqlite;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.web.app.dao.model.User user = dbSqlite.getLoginUser(username.toLowerCase());

        UserDetails userDetails = User
                .withUsername((user.getNickname()))
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
        return userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
