package com.project.authservice.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.authservice.Entity.User;
import com.project.authservice.FeignCall.UserserviceFeign;
import com.project.authservice.Jwt.Tokengen;

@RestController
@RequestMapping("/authservice")
public class AuthController {

    @Autowired
    private Tokengen tokengen;

    @Autowired
    private UserserviceFeign userserviceFeign;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired 
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/login") //✅
    public String login(@RequestParam("name")String username,@RequestParam("password")String passwordString)
    {
        
        System.out.println(username);
        try {
   
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, passwordString));
        System.out.println("User is successfully authenticated");
 

        } catch (Exception e) {

            System.out.println("Invalid credentials");
            e.printStackTrace();

        }


       String token= tokengen.getToken(username);

        return token;
    }
  

    @RequestMapping("/signup") //✅
    public String signup(@Valid @RequestBody User user,BindingResult result)
    { 
        if(result.hasErrors())
        {
            return "Errors";

        }

      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));  
      String done= userserviceFeign.saveUser(user);

        System.out.println(done);

   String token= tokengen.getToken(user.getName());

     return done+" And Your token is :"+token;

    }

}
 