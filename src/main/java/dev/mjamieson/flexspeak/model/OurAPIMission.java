package dev.mjamieson.flexspeak.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // ALLArgs was needed in hubspot integration for a post/update obj -might not be needed in alpineiq
@JsonInclude(JsonInclude.Include.NON_NULL) //allow serialization of data
public class OurAPIMission {
    private String ourName;
    private Integer ourFlight;
}
