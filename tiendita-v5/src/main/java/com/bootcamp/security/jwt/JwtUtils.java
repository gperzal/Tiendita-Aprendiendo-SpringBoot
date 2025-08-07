package com.bootcamp.security.jwt;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final Key signingKey;
    private final long expirationMs;

    public JwtUtils() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String secret = dotenv.get("JWT_SECRET", "defaultSecretKey12345678901234567890");
        this.expirationMs = Long.parseLong(dotenv.get("JWT_EXPIRATION_MS", "3600000"));
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generar JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extraer username (subject)
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validar JWT
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
