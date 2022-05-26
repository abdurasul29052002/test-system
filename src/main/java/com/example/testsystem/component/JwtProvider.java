package com.example.testsystem.component;

import com.example.testsystem.entity.enums.AuthorityType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    public final String secretWord = "ThisIsSecretWord1234567890";
    public final long expirationTime = 1000*60*60*24;


    public String generateToken(String username, AuthorityType authorityType){
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("role",authorityType);
        return Jwts
                .builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512,secretWord)
                .compact();
    }

    public String getUsernameFromToken(String token){
        Claims body = Jwts
                .parser()
                .setSigningKey(secretWord)
                .parseClaimsJws(token)
                .getBody();
        String username = (String) body.get("username");
        return username;
    }
}
