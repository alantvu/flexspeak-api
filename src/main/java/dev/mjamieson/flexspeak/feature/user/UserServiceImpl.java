package dev.mjamieson.flexspeak.feature.user;

import dev.mjamieson.flexspeak.feature.aac.AAC_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AAC_Repository aac_repository;

    @Override
    public UserDTO get(String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        long count = aac_repository.count();

        return UserDTO.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .aacCount(count)
                .build();
    }
}
