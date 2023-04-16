package dev.mjamieson.flexspeak.config;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

//jwt.secret=566D597133743677397A244226452948404D635166546A576E5A723475377821
@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JWTConfiguration {
    private SecretKey key;
    private String secret;
    private long accessTokenExpirationTime;
    private long refreshTokenExpirationTime;

    @PostConstruct
    private void init() {

        System.out.println("Keysssssaas: \"" + secret + "\"");
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        key = Keys.hmacShaKeyFor(decodedKey);
        // Print the key for debugging purposes
    }
}