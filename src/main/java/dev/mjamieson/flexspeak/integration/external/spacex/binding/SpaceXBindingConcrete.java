package dev.mjamieson.flexspeak.integration.external.spacex.binding;

import dev.mjamieson.flexspeak.integration.request.RequestAPIBindingBase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

public class SpaceXBindingConcrete extends RequestAPIBindingBase implements SpaceXBinding {
    private final String apiURL;

    public SpaceXBindingConcrete(String apiURL) {
        this.apiURL = apiURL;
    }
    @Override
    public void init() {
        super.init();
    }

    @Override
    public String getApiUrl() {
        return UriComponentsBuilder.fromHttpUrl(apiURL).build().toString();
    }


    protected HttpHeaders setHttpHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
