package dev.mjamieson.flexspeak.feature.user;

import dev.mjamieson.flexspeak.feature.aac.AAC_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AAC_Repository aac_repository;

    @Override
    public UserDTO get(String username) {


        return UserDTO.builder()
                .firstname("llol")
                .build();
    }
}
