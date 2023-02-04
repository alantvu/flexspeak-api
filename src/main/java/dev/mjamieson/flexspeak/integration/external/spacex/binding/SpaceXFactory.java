package dev.mjamieson.flexspeak.integration.external.spacex.binding;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpaceXFactory {

    @Value("${spaceX.api-url}")
    private String apiURL;

    private SpaceXBinding currentBinding;

    @PostConstruct
    private void initApiBinding() {
        currentBinding = new SpaceXBindingConcrete(apiURL);
        currentBinding.init();
    }


    public SpaceXBinding getAPIBinding() {
        return currentBinding;
    }


}