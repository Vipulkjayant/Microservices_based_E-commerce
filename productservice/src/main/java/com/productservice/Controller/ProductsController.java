package com.productservice.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.productservice.Entity.Product;
import com.productservice.Entity.ProductStock;
import com.productservice.Service.ProductService;

@RestController
@RefreshScope
@RequestMapping("/productService")
public class ProductsController {
    

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Autowired
    private ProductService productService;

    @PostMapping("/createProduct") //✅
    public Product createProduct(@RequestBody Product product,@RequestParam("availablestock")int stock)
    {

        System.out.println("Product is :"+product);
 
     Product product2= productService.createProduct2(product);

     System.out.println("Your product is successfully created"+product2);

     ProductStock productStock=new ProductStock();
     productStock.setProductId(product2.getProductId());
     productStock.setAvailablestock(stock);

     //sending the product stock data to rabbitmq server

     try {
        amqpTemplate.convertAndSend("direct-exchange","product.created",productStock);
        System.out.println("Product-created event is created");

     } catch (Exception e) {
        // TODO: handle exception
     }

     return product2;

    }


    @GetMapping("/getProduct/{id}") //✅
    public Product getproduct(@PathVariable("id")int id)
    {

     Product product= productService.getproduct2(id);


     return product;
    }


    //applying redis for this api , because this api will consume more load

    @GetMapping("/getAllProducts") // Redis-💡
    public List<Product> getAllProducts()
   {

     List<Product> products= productService.getAllProducts2();

     return products;
   }

   @PutMapping("/updateProduct/{id}") //✅
   public Product updateProduct(@PathVariable("id")int id,@RequestBody Product product)
   {
   
    Product product2=  productService.updateProduct2(id,product);


    return product2;
   }

   @DeleteMapping("/deleteProduct/{id}") //✅
   public String deleteProduct(@PathVariable("id")int id)
   {

     String done= productService.deleteProduct2(id);

      Map<String,Object> map=new HashMap<>();
      map.put("productId", id);

     try {
      
       amqpTemplate.convertAndSend("direct-exchange","product.deleted",map);
       System.out.println("Product-deleted event is created");

     } catch (Exception e) {
      // TODO: handle exception
     }


     return done;
   }

   @DeleteMapping("/deleteAllProducts") //✅
   public String deleteAllProducts()
   {

     String done= productService.deleteAllProducts();

  
     try {
      
      amqpTemplate.convertAndSend("direct-exchange","product.AllDeleted",done);

     } catch (Exception e) {
         System.out.println("Getting exception in deleting All Products");
    }

     return done;
   }

   }
