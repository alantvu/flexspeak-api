package dev.mjamieson.flexspeak.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // ALLArgs was needed in hubspot integration for a post/update obj -might not be needed in alpineiq
@JsonInclude(JsonInclude.Include.NON_NULL) //allow serialization of data
public class OurAPICapsule {
    private String ourSerial;
    private String ourCapsule;
    private String ourStatus;
    private List<OurAPIMission> ourMissions;

}
