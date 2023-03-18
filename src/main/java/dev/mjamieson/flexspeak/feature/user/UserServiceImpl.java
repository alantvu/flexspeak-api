package dev.mjamieson.flexspeak.feature.user;

import dev.mjamieson.flexspeak.feature.aac.AAC_Repository;
import dev.mjamieson.flexspeak.feature.user.auth.AuthenticationRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AAC_Repository aac_repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO get(String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        long count = aac_repository.countByUser(user);

        return UserDTO.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .aacCount(count)
                .build();
    }

    @Override
    @Transactional
    public Void delete(@AuthenticationPrincipal UserDetails userDetails, AuthenticationRequest authenticationRequest) {
        // Check if email matches
        String email = userDetails.getUsername();
        if (!email.equals(authenticationRequest.getEmail())) {
            throw new IllegalArgumentException("Emails do not match");
        }

        // Check if passwords match
        String plainTextPassword = authenticationRequest.getPassword();
        String encodedPassword = userDetails.getPassword();
        if (!passwordEncoder.matches(plainTextPassword, encodedPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        // Delete user
        User user = userRepository.findByEmail(email).orElseThrow();
        aac_repository.deleteByUser(user);
        userRepository.delete(user);
        return null;
    }

}
