package com.userservice.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.Entity.User;
import com.userservice.Service.UserService;


@RestController
@RequestMapping("/userservice")
public class UserController {



  @Autowired
  private UserService userService;

    @GetMapping("/verifyUser")
    public User verify(@RequestParam String name)
    {
      System.out.println(name);
     
     User user= userService.getUser(name);
      
      return user;
    }

    @PostMapping("/saveUser") //✅
    public String name(@RequestBody User user)
    {

      System.out.println(user);


     boolean res= userService.saveUser(user);

     if(!res)
     {

      return "Error ! User not saved to db";
     }

      return "User is successfully saved to db";
    }

    @GetMapping("/getUser") //✅
    public User getUser(@RequestParam("username") String username)
    {
        
       User user= userService.getUser(username);

       System.out.println(user);

        return user;
    }


    @GetMapping("/getAllUsers") //✅
    public List<User> getAllUsers()
    {

       List<User> users= userService.getAllUser2();

       return users;
       
    }


    @PutMapping("/updateUser/{id}") //✅
    public User updateUser(@PathVariable("id")int id,@RequestBody User user)
    {
      User user2= userService.updateUser2(id,user);

      return user2;
    }

    @DeleteMapping("/deleteUser/{id}") //✅
    public String user(@PathVariable("id")int id)
    {
      
      return userService.deleteUser2(id);
    }


    @DeleteMapping("deleteAllUsers")  //✅
    public String deleteAllUsers()
    {
 
      return userService.deleteAllUsers2();

    }

    
}