package com.sorokaandriy.springsecurityjwt.webtocken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    private static final String SECRET_KEY = "83DA6861BD7E67E307529CF9958AEDD93ED3901F524353E10EEF1459FCEDB6706400E377C6C181BEDBC5CDE8D149AB5451D9B27C2471B17EB7B4D3C986835D35";
    private static final long DURATION = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails){
        Map<String,String> claims = new HashMap<>();
        claims.put("music","https://open.spotify.com/user/ass1ynf8uvsvag3m47i384eew?si=ad08e168d3774325");
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(DURATION)))
                .signWith(generateKey())
                .compact(); // transform into string
    }

    //
    private SecretKey generateKey(){
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    // check signature & returning claims
    public Claims getClaims(String jwt){
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    // return username from token
    public String extractUsername(String jwt) {
            Claims claims = getClaims(jwt);
            return claims.getSubject();
    }

    // check if the token expiration date has not expired
    public boolean isTokenDurationValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }
}
