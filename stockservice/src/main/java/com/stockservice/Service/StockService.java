package com.stockservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockservice.Controller.StockController;
import com.stockservice.DAO.StockRepo;
import com.stockservice.Entity.Stock;
import com.stockservice.Entity.StockProduct;

import jakarta.transaction.Transactional;

@Service
public class StockService {

 

    @Autowired
    private StockRepo stockRepo;

    
    public Stock createStock(StockProduct stockProduct)
    {
       Stock stock=new Stock();
       stock.setProductId(stockProduct.getProductId());
       stock.setAvailableStock(stockProduct.getAvailablestock());

      Stock stock2= stockRepo.save(stock);

      return stock2;
    }

    public Stock getStock2(int id) {


       Stock stock= stockRepo.findById(id).orElse(null);

       return stock;
    }

    @Transactional
    public Stock updateStock2(int id,int val) {
        
       Stock stock= stockRepo.findById(id).orElse(null);

       if(stock==null)
       {
         return  null;
       }

       if(stock.getAvailableStock()==val) //No need for updating the data in db , remove unecessary db calls

       {
            return stock;

       }

       stock.setAvailableStock(val);
       stockRepo.save(stock);

       return stock;

    }
    

    public boolean deleteStock(int product_id)
    {

       Stock stock=  stockRepo.findStockByProductId(product_id);
         
       if(stock==null)
       {

         return false;

       }

       stockRepo.deleteById(stock.getStockId());


       return true;

      }

   public boolean deleteAllStocks() {
    
       stockRepo.deleteAll();


       return true;
   }


}
