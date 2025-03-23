package com.productservice.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.DAO.ProductRepo;
import com.productservice.Entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
    
  
    @Autowired
    private ProductRepo productRepo;


    //manually update the cache 

    @Autowired
    private CacheManager cacheManager;

   @PersistenceContext
    private EntityManager entityManager;

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON Mapper



@Transactional
@CachePut(value = "products", key = "#result.getProductId()")
public Product createProduct2(Product product) {

    Product res=new Product();
    // product.setProductId(null); //This is required because hb tries to provide the id automatically , but i have deleted the whole table , done by old transaction so transaction mismatched problem


    try {
     Product  product2 = productRepo.save(product);
     res=product2;
    } catch (Exception e) {
        System.out.println(e);
    }

    Cache cache = cacheManager.getCache("products");

    if (cache != null) {
        // ✅ Initialize if null
        List<Product> list = cache.get("allProducts", List.class);
        if (list == null) {

            list = productRepo.findAll(); // ✅ Load from DB if cache is empty
        }
        list.add(res); // ✅ Add new product to list
        
        cache.put("allProducts", list);
    }

    return res;
}

    public Product getproduct2(int id) {


      Product product= productRepo.findById(id).orElse(null);

      return product;

    }

    //Fetching record from the redis, with key=allProducts and the value is returned by this method

    @Cacheable(value = "products",key = "'allProducts'")
    public List<Product> getAllProducts2() {

      List<Product> products=  productRepo.findAll();
      System.out.println("Database call...");
      
      return products;
    }

    public Product updateProduct2(int id,Product product) {
      
       Product product2= productRepo.findById(id).orElse(null);

       product2.setProductName(product.getProductName());
       product2.setProductPrize(product.getProductPrize());

       productRepo.save(product2);

       return product2;
    }
    @CacheEvict(value = "products", key = "'allProducts'") // Clears 'allProducts' cache
    public String deleteProduct2(int id) {
        
        productRepo.deleteById(id);
        System.out.println("Product is successfully from db");
    
        Cache cache = cacheManager.getCache("products");
        
        if (cache != null) {

            List<Product> list = cache.get("allProducts", List.class);
    
              
              list.removeIf(product->product.getProductId()==id);

               cache.put("allProducts", list);


            }
        
    
        return "Product is successfully deleted";
    }
  
    
    @CacheEvict(value = "products",allEntries = true)
    public String deleteAllProducts() {
    
        productRepo.deleteAll();

        entityManager.clear();
       
        return "All products are successfully deleted  !!";
    }
    

}
