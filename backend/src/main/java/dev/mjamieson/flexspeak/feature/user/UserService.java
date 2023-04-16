package dev.mjamieson.flexspeak.feature.user;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.user.auth.AuthenticationRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDTO get(@CurrentUsername String username);

    Void delete(@AuthenticationPrincipal UserDetails userDetails, AuthenticationRequest authenticationRequest);
}
