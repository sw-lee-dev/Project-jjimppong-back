package com.ateam.jjimppong_back.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// class: JWT 생성 및 검증 기능 제공자 //
@Component
public class JwtProvider {
  
  @Value("${jwt.secret}")
  private String secretKey;

  // function: JWT 생성 메소드 //
  public String create(String userId) {

    Date expiration = Date.from(Instant.now().plus(9, ChronoUnit.HOURS));
    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    String jwt = null;

    try {

      jwt = Jwts.builder()
        .signWith(key, SignatureAlgorithm.HS256)
        .setSubject(userId)
        .setIssuedAt(new Date())
        .setExpiration(expiration)
        .compact();

    } catch (Exception exception) {
      exception.printStackTrace();
    }
    
    return jwt;
  }

  // function: JWT 검증 메소드 //
  public String validate(String jwt) {

    String userId = null;

    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    try {

      userId = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(jwt)
        .getBody()
        .getSubject();

    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return userId;
  }

}
