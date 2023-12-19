package com.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static String signKey = "scutWeb";
    private static Long expire = 2*43200000L; //24个小时的过期时间

    public static String generateJwt(Map<String,Object> claims){
        return Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256,signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
    }
    public static Claims parseJWT(String jwt) {
        return Jwts.parser()
                //设置指定密钥
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
