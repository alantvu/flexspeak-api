package dev.mjamieson.flexspeak.feature.integration.flat_icon.binding;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FlatIconFactory {

    @Value("${flatIcon.api-url}")
    private String apiURL;
    @Value("${flatIcon.api-key}")
    private String apiKey;


    private FlatIconBinding currentBinding;

    @PostConstruct
    private void initApiBinding() {
        currentBinding = new FlatIconBindingConcrete(
                apiURL,
                apiKey
        );
        currentBinding.init();
    }

    @PreDestroy
    public void shutdown() {

        currentBinding.getExecutorService().shutdown();
    }

    public FlatIconBinding getAPIBinding() {
        return currentBinding;
    }


}