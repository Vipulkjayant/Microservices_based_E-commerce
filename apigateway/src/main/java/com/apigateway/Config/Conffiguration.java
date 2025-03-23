package com.apigateway.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class Conffiguration {
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        System.out.println("This is the config class");


        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchange -> exchange
                
            .anyExchange().permitAll() // Other requests need authentication
            )
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable) // 🔹 Disable default login form
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable) // 🔹 Disable default HTTP Basic Auth
            .build();
    }

}
