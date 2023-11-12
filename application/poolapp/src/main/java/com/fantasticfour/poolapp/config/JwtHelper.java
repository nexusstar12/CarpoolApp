package com.fantasticfour.poolapp.config;

import com.fantasticfour.poolapp.services.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; //TOKEN VALID FOR  5HRS.

    public String getUserNameFromJwtToken(String token) {
        return getClaimFromJwtToken(token,Claims::getSubject);
    }

    public Date getExpirationDateFromJwtToken(String token) {
        return getClaimFromJwtToken(token,Claims::getExpiration);
    }

    public <T> T getClaimFromJwtToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromJwtToken(token);
        return claimsResolver.apply(claims);
    }

    private Key getSigninKey() { //512 byte secret key
        byte[] key= Decoders.BASE64.decode("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

        return Keys.hmacShaKeyFor(key);

    }

    private Claims getAllClaimsFromJwtToken(String token) {

        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromJwtToken(token);
        return expiration.before(new Date());
    }

//    public String generateToken(UserDetails userDetails) {
//
//        Map<String,Object> claims=new HashMap<>();
//
//
//        return generateJwtToken(claims,userDetails.getUsername());
//
//    }
public String generateToken(CustomUserDetails userDetails) {

    Map<String,Object> claims=new HashMap<>();
    claims.put("userId", userDetails.getUser().getUserId());

    return generateJwtToken(claims,userDetails.getUsername());

}

    private String generateJwtToken(Map<String,Object> claims,String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
                .signWith(getSigninKey(), SignatureAlgorithm.HS512).compact();
    }

    public Boolean validateJwtToken(String token,UserDetails userDetails) {
        final String username = getUserNameFromJwtToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }
}
