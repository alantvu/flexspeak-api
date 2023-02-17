package dev.mjamieson.flexspeak.user.config;

import dev.mjamieson.flexspeak.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    private Long accessTokenExpirationTime;

    private Long refreshTokenExpirationTime;

    @Value("${jwt.access-token-expiration-time}")
    public void setAccessTokenExpirationTime(String accessTokenExpirationTime) {
        this.accessTokenExpirationTime = Long.parseLong(accessTokenExpirationTime);
    }

    @Value("${jwt.refresh-token-expiration-time}")
    public void setRefreshTokenExpirationTime(String refreshTokenExpirationTime) {
        this.refreshTokenExpirationTime = Long.parseLong(refreshTokenExpirationTime);
    }
    private SecretKey key;

    private final Clock clock;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getEmail(), accessTokenExpirationTime);
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("refreshToken", true);
        return createToken(claims, user.getEmail(), refreshTokenExpirationTime);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean canTokenBeRefreshed(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("refreshToken") != null && !isTokenExpired(token);
    }

    public String refreshToken(String refreshToken, String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("refreshToken", true);
        return createToken(claims, userEmail, refreshTokenExpirationTime);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(Date.from(clock.instant()));
    }
    public boolean verifyRefreshToken(String refreshToken, User user) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            if (!claims.containsKey("refreshToken") || !claims.get("refreshToken", Boolean.class)) {
                return false;
            }

            String email = claims.getSubject();
            return email.equals(user.getEmail()) && !isTokenExpired(refreshToken);
        } catch (JwtException e) {
            return false;
        }
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private String createToken(Map<String, Object> claims, String subject, Long expirationTime) {
        Instant now = Instant.now(clock);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expirationTime, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }
}

