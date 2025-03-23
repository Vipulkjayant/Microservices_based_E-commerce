package com.project.authservice.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.authservice.Entity.User;
import com.project.authservice.FeignCall.UserserviceFeign;

public class CustomUserDetailsService implements UserDetailsService{


   
    @Autowired
    private UserserviceFeign uf;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   
    
    User user= uf.getUser(username);
    System.out.println(user);

    
   return new CustomUserDetails(user);

    }

}
