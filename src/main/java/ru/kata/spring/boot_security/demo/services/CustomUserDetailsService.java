package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDAO.getUserByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        System.err.println(user.getUsername() + " " + user.getPassword()
                + " " + user.getRoleName());

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
        return userDetails;
    }
}
