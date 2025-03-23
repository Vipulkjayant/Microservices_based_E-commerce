package com.stockservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stockservice.Entity.Stock;
import com.stockservice.Service.StockService;

@RestController
@RequestMapping("/stockService")
public class StockController {
  

    @Autowired
    private StockService stockService;

    @GetMapping("/getStock/{id}") //✅
    public ResponseEntity<?> getStock(@PathVariable("id")int id)
    {
       Stock stock= stockService.getStock2(id);
       if(stock==null)
       {
         return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found");
       }

       return ResponseEntity.ok().body(stock);
    }

    @PutMapping("/updateStock/{id}")
    public ResponseEntity<?> updateStock(@RequestParam("stockValue")int val,@PathVariable("productId")int id)
    {
       Stock stock=  stockService.updateStock2(id,val);

       if(stock==null)
       {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found");
      }

       return ResponseEntity.ok().body(stock);
    }

    
}