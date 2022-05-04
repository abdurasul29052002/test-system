package com.example.testsystem.component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    public final String secretWord = "ThisIsSecretWord1234567890";
    public final long expirationTime = 1000*60*60*24;


    public String generateToken(String username){
        return Jwts
                .builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512,secretWord)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(secretWord)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
