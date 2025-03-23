package com.userservice.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.DAO.UserRepo;
import com.userservice.Entity.User;

@Service
public class UserService {
    

    @Autowired
    private UserRepo userRepo;

    public User getUser(String username)
    {


       User user= userRepo.getUserByName(username);

       if(user==null)
       {

        return null;
       }

        return user;
        
    }

    public boolean saveUser(User user)
    {

       User user2= userRepo.save(user);

       if(user2==null)
       {

         return false;

       }
       
       
       return true;

    }

    public List<User> getAllUser2() {

       List<User> users= userRepo.findAll();

       if(users==null)
       {
       
        return null;

       }

       return users;

    }

    public User updateUser2(int id, User user) {

      User user2=  userRepo.findById(id).orElse(null);

      if(user2==null)
      {
        return null;
      }

      user2.setName(user.getName());
      user2.setEmail(user.getEmail());
      user2.setRole(user.getRole());


      userRepo.save(user2);

      return user2;

    }

    public String deleteUser2(int id) {

       User user= userRepo.findById(id).orElse(null);
       
       if(user==null)
       {
          return "User not exists";
       }

       userRepo.delete(user);

       return "User deleted successfully !!";
    }

    public String deleteAllUsers2() { 

        userRepo.deleteAll();

        return "All users are deleted successfully !!";

       }

}
