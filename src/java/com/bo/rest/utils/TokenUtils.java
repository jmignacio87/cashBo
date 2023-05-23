package com.bo.rest.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

public class TokenUtils {

    private static final Key KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    public static String generateJwt(String subject) {
        try {

            Date now = new Date();
            Date expiration = new Date(now.getTime() + Duration.ofDays(1).toMillis());

            return Jwts.builder()
                    .setSubject(subject)
                    .setIssuedAt(now)
                    .setExpiration(expiration)
                    .signWith(KEY)
                    .compact();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean verifyJwt(String jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(jwt);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getSubject(String jwt) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            return "";
        }
    }

}
