package com.project.authservice.Security;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception
    {
       http

        .authorizeHttpRequests(auth->auth
        .anyRequest().permitAll()

        )
        .csrf().disable()

        .formLogin().disable();


        return http.build();
        
    }

   
    @Bean
    public UserDetailsService userDetailsService()
    {

        return new CustomUserDetailsService();

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {

        return new BCryptPasswordEncoder();
    }

   

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,BCryptPasswordEncoder passwordEncoder)
    {

     DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
     daoAuthenticationProvider.setUserDetailsService(userDetailsService);
     daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

     return new ProviderManager(daoAuthenticationProvider);


    }
 


}
