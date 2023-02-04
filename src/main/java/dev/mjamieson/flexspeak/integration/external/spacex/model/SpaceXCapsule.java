package dev.mjamieson.flexspeak.integration.external.spacex.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.mjamieson.flexspeak.model.OurAPICapsule;
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
public class SpaceXCapsule {
    private String capsule_serial;
    private String capsule_id;
    private String status;
    private String original_launch;
    private Integer original_launch_unix;
    private List<SpaceXMission> missions;
    private Integer landings;
    private String type;
    private String details;
    private Integer reuse_count;

    public static SpaceXCapsule from(OurAPICapsule ourAPICapsule){
        List<SpaceXMission> spaceXMissions = SpaceXMission.listFrom(ourAPICapsule.getOurMissions());
        return SpaceXCapsule.builder()
                .capsule_serial(ourAPICapsule.getOurSerial())
                .status(ourAPICapsule.getOurStatus())
                .missions(spaceXMissions)
                .build();
    }

}
/**
 * {"capsule_serial":"C101",
 * "capsule_id":"dragon1",
 * "status":"retired",
 * "original_launch":"2010-12-08T15:43:00.000Z",
 * "original_launch_unix":1291822980,
 * "missions":[{"name":"COTS 1","flight":7}],
 * "landings":1,
 * "type":"Dragon 1.0",
 * "details":"Reentered after three weeks in orbit",
 * "reuse_count":0}
 * */
