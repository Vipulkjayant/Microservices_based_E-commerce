package com.apigateway.Security;

import java.nio.charset.StandardCharsets;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import com.apigateway.ApigatewayApplication;
import com.apigateway.DTO.UserDTO;
import com.apigateway.FeignClients.UserserviceFeign;

import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@Component
public class JwtAuthFilter implements WebFilter {


    @Autowired
    private JwtUtil jwtutil;

    @Autowired
    private UserserviceFeign userserviceFeign;



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        // ✅ Skip JWT validation for public URLs
        if (path.startsWith("/apigateway/authservice/") || path.startsWith("/apigateway/userservice/") || path.startsWith("/apigateway/productService/") || path.startsWith("/apigateway/stockService/")) {


            System.out.println("Founded *authservice or * userservice or *productService in path..");
            return chain.filter(exchange);
        }
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            System.out.println("Founded *header is null");

           exchange.getResponse().setRawStatusCode(HttpStatus.SC_BAD_GATEWAY); // 403 Forbidden
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            
            String responseBody = "{\"message\": \"Access Denied! Your request is blocked.\"}";

            byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
            return exchange.getResponse().writeWith(Mono.create((MonoSink<byte[]> sink) -> {
                sink.success(bytes);
            }).map(exchange.getResponse().bufferFactory()::wrap));
         }
    
        String token = authHeader.substring(7);
        String username = jwtutil.extractUserName(token);
    
        // 🔹 Fetch user details from the UserService via Feign
        UserDTO userDTO = userserviceFeign.verify(username);
        if (userDTO == null) {

            System.out.println("Founded no user with this token from userdetails");
            return exchange.getResponse().setComplete();
        }
    
        // 🔹 Convert UserDTO to UserDetails
        UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername(userDTO.getName())
            .password(userDTO.getPassword())
            .roles("USER")  // You might want to fetch actual roles
            .build();
    
        // 🔹 Validate the JWT
        if (!jwtutil.validate(token, userDetails)) {

            System.out.println("Your token is expired");
            return exchange.getResponse().setComplete();
        }
    
        // 🔹 Set authentication in reactive security context
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext securityContext = new SecurityContextImpl(authToken);
    
        return chain.filter(exchange)
            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
    }
}
