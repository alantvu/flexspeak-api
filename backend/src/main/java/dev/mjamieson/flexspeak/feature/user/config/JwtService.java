package dev.mjamieson.flexspeak.feature.user.config;

import dev.mjamieson.flexspeak.config.JWTConfiguration;
import dev.mjamieson.flexspeak.feature.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
    private final Clock clock;
    private final JWTConfiguration jwt_configurationProperty;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getEmail(), jwt_configurationProperty.getAccessTokenExpirationTime());
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("refreshToken", true);
        return createToken(claims, user.getEmail(), jwt_configurationProperty.getRefreshTokenExpirationTime());
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

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(Date.from(clock.instant()));
    }

    public boolean verifyRefreshToken(String refreshToken, String userEmail) {
        try {
            Claims claims = parseToken(refreshToken);
            if (!claims.containsKey("refreshToken") || !claims.get("refreshToken", Boolean.class)) {
                return false;
            }
            String email = claims.getSubject();
            return email.equals(userEmail) && !isTokenExpired(refreshToken);
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean checkRefreshToken(String refreshToken, String userEmail) {
        try {
            Claims claims = parseToken(refreshToken);
            if (!claims.containsKey("refreshToken") || !claims.get("refreshToken", Boolean.class)) {
                return false;
            }
            String email = claims.getSubject();
            return email.equals(userEmail);
        } catch (JwtException e) {
            return false;
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwt_configurationProperty.getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private String createToken(Map<String, Object> claims, String subject, Long expirationTime) {
        Instant now = Instant.now(clock);
        Date from = Date.from(now.plus(expirationTime, ChronoUnit.SECONDS));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expirationTime, ChronoUnit.SECONDS)))
//                .setExpiration(Date.from(now.plus(expirationTime, ChronoUnit.MINUTES)))
                .signWith(jwt_configurationProperty.getSigningKey())
                .compact();
    }
}


