package com.project.back_end.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    // Token validity: 1 hour
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;

    // Signing key used to sign and validate JWT
    private final Key signingKey = Keys.secretKeyFor(
            SignatureAlgorithm.HS256
    );

    /**
     * Returns the signing key used for JWT generation and validation.
     */
    public Key getSigningKey() {
        return signingKey;
    }

    /**
     * Generates a JWT token for the given email.
     */
    public String generateToken(String email) {

        Date now = new Date();
        Date expiryDate = new Date(
                now.getTime() + EXPIRATION_TIME
        );

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the email (subject) from a JWT token.
     */
    public String getEmailFromToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Validates the JWT token.
     */
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
