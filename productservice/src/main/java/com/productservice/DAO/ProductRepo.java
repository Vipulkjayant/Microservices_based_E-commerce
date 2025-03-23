package com.productservice.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productservice.Entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{

    
    
}
