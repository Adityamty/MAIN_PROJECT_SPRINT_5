//Handle JWT token validation and creation.
package com.catalogservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {
    private final String SECRET = "mySecretJWTKey123456789012345678"; // ✅ Confirm with auth team

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        Claims claims = extractClaims(token);
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof java.util.List<?> rolesList) {
            return rolesList.get(0).toString();
        } else if (rolesObj instanceof String role) {
            return role;
        }
        return null;
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired", e);
        } catch (SignatureException | MalformedJwtException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    //extractClaims will return this:
    //        {
    //            "sub": "test.cmsadmin@example.com",
    //                "roles": ["cms_admin"],
    //            "iat": 1752149981,
    //                "exp": 1952236381
    //        }
}
