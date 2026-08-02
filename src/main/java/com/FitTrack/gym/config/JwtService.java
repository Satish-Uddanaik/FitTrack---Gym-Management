package com.FitTrack.gym.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24; // 1 day

    private static final String SECRET =
            "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";

    private final SecretKey key = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8)
    );

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token,
                              Function<Claims, T> resolver) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return resolver.apply(claims);
    }

    public boolean isTokenValid(String token,
                                UserDetails userDetails) {

        return extractUsername(token).equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration)
                .before(new Date());
    }
}

//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Service
//@RequiredArgsConstructor
//public class JwtService {
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.expiration}")
//    private long jwtExpiration;
//
//    /**
//     * Generate JWT Token
//     */
//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    /**
//     * Generate JWT Token with extra claims
//     */
//    public String generateToken(
//            Map<String, Object> extraClaims,
//            UserDetails userDetails
//    ) {
//
//        return Jwts.builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(
//                        new Date(System.currentTimeMillis() + jwtExpiration)
//                )
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    /**
//     * Extract Username
//     */
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    /**
//     * Validate Token
//     */
//    public boolean isTokenValid(
//            String token,
//            UserDetails userDetails
//    ) {
//
//        final String username = extractUsername(token);
//
//        return username.equals(userDetails.getUsername())
//                && !isTokenExpired(token);
//    }
//
//    /**
//     * Check Expiration
//     */
//    private boolean isTokenExpired(String token) {
//
//        return extractExpiration(token)
//                .before(new Date());
//
//    }
//
//    /**
//     * Extract Expiration Date
//     */
//    private Date extractExpiration(String token) {
//
//        return extractClaim(token, Claims::getExpiration);
//
//    }
//
//    /**
//     * Generic Claim Extractor
//     */
//    public <T> T extractClaim(
//            String token,
//            Function<Claims, T> claimsResolver
//    ) {
//
//        final Claims claims = extractAllClaims(token);
//
//        return claimsResolver.apply(claims);
//
//    }
//
//    /**
//     * Extract All Claims
//     */
//    private Claims extractAllClaims(String token) {
//
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//    }
//
//    /**
//     * Secret Key
//     */
//    private Key getSigningKey() {
//
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//
//        return Keys.hmacShaKeyFor(keyBytes);
//
//    }
//
//}