package dev.mjamieson.flexspeak.feature.user;

import dev.mjamieson.flexspeak.feature.user.auth.AuthenticationRequest;
import dev.mjamieson.flexspeak.feature.user.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserDTO> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDto = userService.get(userDetails.getUsername());
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails userDetails,@RequestBody AuthenticationRequest authenticationRequest) {
        userService.delete(userDetails,authenticationRequest);
        return ResponseEntity.ok(null);
    }


}
