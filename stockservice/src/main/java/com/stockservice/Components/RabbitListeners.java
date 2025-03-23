package com.stockservice.Components;

import java.util.Map;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockservice.Entity.StockProduct;
import com.stockservice.Service.StockService;

@Component
public class RabbitListeners {
    

    @Autowired
    private StockService stockService;

    
@RabbitListener(queues = "stockQueue.created", containerFactory = "rabbitListenerContainerFactory") 
public void getStockQueue(StockProduct stockProduct) {



    try {
        System.out.println(stockService.createStock(stockProduct));
    } catch (Exception e) {
        e.printStackTrace();
        throw new AmqpRejectAndDontRequeueException("Processing failed, discarding message", e);
    }


}


@RabbitListener(queues = "stockQueue.deleted",containerFactory = "rabbitListenerContainerFactory")
public void getStockQueue2(Map<String,Object> map){

    try {
        
   
   int id= (int)map.get("productId");
  boolean res= stockService.deleteStock(id);

  if(res==true)
  {
    System.out.println("Stock is successfully deleted from db");
  }

  else{

    System.out.println("Already deleted.....");
  }

} catch (Exception e) {

    e.printStackTrace();
}

}

@RabbitListener(queues = "stockQueue.AllDeleted",containerFactory = "rabbitListenerContainerFactory")
public void getStockQueue3(String str){

    System.out.println(str);

   boolean res= stockService.deleteAllStocks();

   if(res)
   {

    System.out.println("All stock entries has been deleted successfully....!!!");
   }

}


}