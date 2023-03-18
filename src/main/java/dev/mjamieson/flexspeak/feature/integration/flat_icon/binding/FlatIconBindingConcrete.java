package dev.mjamieson.flexspeak.feature.integration.flat_icon.binding;

import dev.mjamieson.flexspeak.feature.integration.request.RequestAPIBindingBase;
import dev.mjamieson.flexspeak.feature.integration.thread.CustomAPIExecutor;
import dev.mjamieson.flexspeak.feature.integration.thread.CustomAPIExecutorConcrete;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.concurrent.Executors;

public class FlatIconBindingConcrete extends RequestAPIBindingBase implements FlatIconBinding {
    private final String apiURL;

    public FlatIconBindingConcrete(String apiURL) {
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

    @Override
    public CustomAPIExecutor createAPIExecutorService() {
        CustomAPIExecutor apiExecutor = new CustomAPIExecutorConcrete("flatIcon");
        // change threads here depending on need
        apiExecutor.init(Executors.newSingleThreadExecutor());
        return apiExecutor;
    }

    protected HttpHeaders setHttpHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
