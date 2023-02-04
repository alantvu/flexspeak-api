package dev.mjamieson.flexspeak.integration.external.spacex.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.mjamieson.flexspeak.model.OurAPIMission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // ALLArgs was needed in hubspot integration for a post/update obj -might not be needed in alpineiq
@JsonInclude(JsonInclude.Include.NON_NULL) //allow serialization of data
public class SpaceXMission {
    private String name;
    private Integer flight;
    public static SpaceXMission from(OurAPIMission ourAPIMission){
        return SpaceXMission.builder()
                .name(ourAPIMission.getOurName())
                .flight(ourAPIMission.getOurFlight())
                .build();
    }

    public static List<SpaceXMission> listFrom(List<OurAPIMission> ourAPIMissions){
        List<SpaceXMission> spaceXMissions = ourAPIMissions.
                parallelStream()
                .map(SpaceXMission::from)
                .collect(Collectors.toList());

        return spaceXMissions;
    }
}
