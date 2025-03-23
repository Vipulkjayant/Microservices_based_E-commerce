package com.stockservice.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.stockservice.Entity.Stock;


@Repository
public interface StockRepo extends JpaRepository<Stock,Integer>{
    
   public Stock findStockByProductId(int productId);
    
}
