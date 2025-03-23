package com.apigateway.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apigateway.DTO.UserDTO;

@FeignClient(name = "USERSERVICE")
public interface UserserviceFeign { 
    
    @GetMapping("userservice/verifyUser")
    public UserDTO verify(@RequestParam String name);
}
