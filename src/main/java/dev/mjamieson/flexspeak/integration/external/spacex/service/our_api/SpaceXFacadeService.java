package dev.mjamieson.flexspeak.integration.external.spacex.service.our_api;


import dev.mjamieson.flexspeak.model.OurAPICapsule;

public interface SpaceXFacadeService {
    void invokeIntegration();

    void postIntegration(OurAPICapsule ourAPICapsule);
}
