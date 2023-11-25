package com.example.task.security;

import com.example.task.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${task.app.secret}")
    private String APP_SECRET;

    @Value("${task.expires.in}")
    private Long EXPIRES_IN;

    public String generateJwtToken(User user) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(user.getId()))
                .setIssuedAt(now).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }

    public String generateJwtToken(User user,Long expire) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expire);
        return Jwts.builder().setSubject(Long.toString(user.getId()))
                .setIssuedAt(now).setExpiration(expireDate)
                .claim("role","User")
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }
    Integer getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return Integer.valueOf(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

}
