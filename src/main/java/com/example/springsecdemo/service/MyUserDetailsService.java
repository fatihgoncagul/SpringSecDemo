package com.example.springsecdemo.service;

import com.example.springsecdemo.dao.UserRepo;
import com.example.springsecdemo.model.User;
import com.example.springsecdemo.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user = userRepo.findByUsername(username);
       if (user ==null){
           System.out.println("User 404");
           throw new UsernameNotFoundException("User404");
       }


        return new UserPrincipal(user);
    }
}
