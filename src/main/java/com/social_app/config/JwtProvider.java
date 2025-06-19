package com.social_app.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import static com.social_app.config.Jwt_Constants.SECRET_KEY;

public class JwtProvider {
    private static final SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
    public static String generateToken(Authentication auth){
        Instant now = Instant.now(); 
        Instant expiry = now.plusSeconds(86400); //token valid for one day
        
        String jwt = Jwts.builder()
                .subject(auth.getName())
                .claim("email", auth.getName())
                .issuer("TaruPathak")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(key, Jwts.SIG.HS512)
                .compact();

        return jwt;
    }
    public static String getEmailFromJwtToken(String jwt){
        try {
            // validate the input parameters
            if(jwt == null || jwt.trim().isEmpty()){
                throw new IllegalArgumentException("Jwt Token can not be null or empty");
            }
            if(key == null){
                throw new IllegalArgumentException("Secret Key cannot be null");
            }
            //Remove "Bearer" prefix if it is present
            jwt = jwt.startsWith("Bearer ") ? jwt.substring(7) : jwt;

            Claims claims = Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();

            String email = claims.get("email", String.class);
            if (email == null || email.isEmpty()) {
                throw new JwtException("Email claim is missing or empty in JWT");
            }

            return email;
        }catch(SignatureException e){
            throw new JwtException("Invalid JWT Signature", e);
        }catch(IllegalArgumentException e){
            throw new JwtException("Jwt is malformed", e);
        }catch(JwtException e){
            throw new JwtException("JWT Validation failed", e);
        }
    }
}
