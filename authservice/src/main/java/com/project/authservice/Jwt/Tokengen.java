package com.project.authservice.Jwt;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64.Decoder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class Tokengen {
    

    private String Secret_Key="";

    public Tokengen() 
    {

     try {
        
        KeyGenerator key=KeyGenerator.getInstance("HmacSHA256");
        SecretKey key2=key.generateKey();
        String sc=Base64.getEncoder().encodeToString(key2.getEncoded()); 
        this.Secret_Key=sc;
     } catch (Exception e) {
     
        e.printStackTrace();

    }


    }

    public String getToken(String username)
    {
       Map<String,Object> map=new HashMap<>();

       return Jwts.builder()
             .claims()
             .add(map)
             .subject(username)

              .issuedAt(new Date(System.currentTimeMillis()))
              .expiration(new Date(System.currentTimeMillis()*1000*60*60*10+1000*60*30)) //10 hours 30 min
              .and()
              .signWith(getKey())
              .compact();
              


    }

    public Key getKey()
    {

       byte[] key=Decoders.BASE64.decode(Secret_Key);

       return Keys.hmacShaKeyFor(key);

    }



}
