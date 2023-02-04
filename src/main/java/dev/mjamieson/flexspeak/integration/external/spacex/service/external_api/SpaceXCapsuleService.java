package dev.mjamieson.flexspeak.integration.external.spacex.service.external_api;

import dev.mjamieson.flexspeak.integration.external.spacex.model.SpaceXCapsule;

import java.util.List;

public interface SpaceXCapsuleService {
    List<SpaceXCapsule> getAllCapsules();

    void postCapsule(SpaceXCapsule spaceXCapsule);
}
