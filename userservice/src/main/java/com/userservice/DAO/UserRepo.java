package com.userservice.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.Entity.User;


@Repository
public interface UserRepo extends JpaRepository<User,Integer>{

   public User getUserByName(String username);
    
  
}
