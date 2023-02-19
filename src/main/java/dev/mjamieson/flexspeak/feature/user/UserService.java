package dev.mjamieson.flexspeak.feature.user;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;

public interface UserService {
    UserDTO get(@CurrentUsername String username);
}
