package dev.mjamieson.flexspeak.feature.user;

import lombok.Builder;
import lombok.Value;

@Builder
public record UserDTO(String firstname, String lastname, String email) {

}