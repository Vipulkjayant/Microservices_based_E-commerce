package com.project.authservice.FeignCall;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.authservice.Entity.User;

@FeignClient(name = "USERSERVICE")
public interface UserserviceFeign {
    

    @PostMapping("/userservice/saveUser") //✅
    public String saveUser(@RequestBody User user);

    @GetMapping("/userservice/getUser")
    public User getUser(@RequestParam String username); 

}
