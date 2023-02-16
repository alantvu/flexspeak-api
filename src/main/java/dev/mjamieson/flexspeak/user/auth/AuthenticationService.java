package dev.mjamieson.flexspeak.user.auth;

import dev.mjamieson.flexspeak.user.Role;
import dev.mjamieson.flexspeak.user.User;
import dev.mjamieson.flexspeak.user.UserRepository;
import dev.mjamieson.flexspeak.user.config.JwtService;
import exception.GeneralMessageException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    public AuthenticationResponse refresh(AuthenticationResponse request) {
        // Verify the refresh token
//        if (!jwtService.verifyRefreshToken(request.getRefreshToken())) {
//            throw new InvalidRefreshTokenException();
//        }

        // Get the user ID from the refresh token
        String userId = jwtService.extractUsername(request.getRefreshToken());

        // Get the user from the repository
        User user = repository.findByEmail(userId).orElseThrow();

        // Generate a new JWT token and refresh token
        String newToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        // Return the new tokens in a new authentication response
        return AuthenticationResponse.builder()
                .token(newToken)
                .refreshToken(newRefreshToken)
                .build();
    }
    public AuthenticationResponse refreshTokens(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization");
        refreshToken = refreshToken.replace("Bearer ", "");

        String email = jwtService.extractUsername(refreshToken);
        User user = repository.findByEmail(email).orElseThrow();

        if (jwtService.canTokenBeRefreshed(refreshToken)) {
            String newAccessToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user);
            return AuthenticationResponse.builder()
                    .token(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        } else {
            throw new GeneralMessageException("Invalid refresh token");
        }
    }
}
