//package com.elice.homealone.global.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import jakarta.annotation.PostConstruct;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Base64;
//import java.util.Date;
//
//@Component
//public class JwtTokenProvider {
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${spring.jwt.token.access-expiration-time}")
//    private long accessExpirationTime;
//
//    @Value("${spring.jwt.token.refresh-expiration-time}")
//    private long refreshExpirationTime;
//
//    @PostConstruct
//    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }
//
//    /**
//     * email을 받아서 access 토큰 생성
//     */
//    public String createAccessToken(String email) {
//        Claims claims = Jwts.claims().setSubject(email);
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + accessExpirationTime);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//
//    /**
//     * email을 받아서 refresh 토큰 생성
//     */
//    public String createRefreshToken(String email) {
//        Claims claims = Jwts.claims().setSubject(email);
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + refreshExpirationTime);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//    /**
//     * JWT 토큰 유효성 검증
//     */
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     * JWT 토큰으로 email 반환받는다.
//     */
//    public String getEmail(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//    }
//
//
//    /**
//     * Header에서 JWT 토큰 추출
//     */
//    public String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}