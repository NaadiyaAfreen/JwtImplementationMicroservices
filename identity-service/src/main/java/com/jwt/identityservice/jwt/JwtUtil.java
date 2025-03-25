package com.jwt.identityservice.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET="0fdcfe192f0c5e12a734805de27e7c7ec68d5cac4caa729bab0f335fcf8e0ddf";


    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,userName);
    }

    private String createToken(Map<String,Object> claims,String userName) {
         return Jwts.builder()
                    .claims(claims)
                    .subject(userName)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+ 1000*60*30))
                    .signWith(getSignKey())
                    .compact();
    }

    public void validateToken(final String token) {
        Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
    }

    private SecretKey getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
