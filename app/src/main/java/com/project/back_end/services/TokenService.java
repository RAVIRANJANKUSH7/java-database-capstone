package com.project.back_end.services;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenService {

    public String generateToken(String email) {
        String tokenData = email + ":" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(tokenData.getBytes());
    }

    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        try {
            Base64.getDecoder().decode(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            String decodedToken =
                    new String(Base64.getDecoder().decode(token));

            return decodedToken.split(":")[0];
        } catch (Exception e) {
            return null;
        }
    }
}
