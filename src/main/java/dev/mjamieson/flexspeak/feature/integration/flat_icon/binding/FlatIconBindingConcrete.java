package dev.mjamieson.flexspeak.feature.integration.flat_icon.binding;

import dev.mjamieson.flexspeak.feature.integration.flat_icon.model.FlatIconApiKey;
import dev.mjamieson.flexspeak.feature.integration.flat_icon.model.FlatIconToken;
import dev.mjamieson.flexspeak.feature.integration.flat_icon.model.FlatIconTokenResponse;
import dev.mjamieson.flexspeak.feature.integration.request.RequestAPIBindingBase;
import dev.mjamieson.flexspeak.feature.integration.request.RequestApi;
import dev.mjamieson.flexspeak.feature.integration.request.ResponseWrapper;
import dev.mjamieson.flexspeak.feature.integration.thread.CustomAPIExecutor;
import dev.mjamieson.flexspeak.feature.integration.thread.CustomAPIExecutorConcrete;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executors;

public class FlatIconBindingConcrete extends RequestAPIBindingBase implements FlatIconBinding {
    private final String apiURL;
    private final String apiKey;

    private final Clock clock;

    private final String version = "v3";
    private FlatIconToken flatIconToken;

    public FlatIconBindingConcrete(String apiURL, String apiKey, Clock clock) {
        this.apiURL = apiURL;
        this.apiKey = apiKey;
        this.clock = clock;
        this.customAPIExecutor = createAPIExecutorService();
    }

    @Override
    public void init() {
        super.init();
        setAuthorizationToken();
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

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public <T> ResponseWrapper<T> makeRequest(URI uri, HttpMethod httpMethod, Class<T> returnClazz) {
        refreshTokenIfExpired();
        return super.makeRequest(uri, httpMethod, returnClazz, null);
    }


    @Override
    public <T> ResponseWrapper<T> makeRequest(URI uri, HttpMethod httpMethod, Class<T> returnClazz, @Nullable Object request) {
        refreshTokenIfExpired();
        HttpEntity httpEntity = new HttpEntity(request, httpHeaders);
        return RequestApi.makeRequest(uri, httpMethod, httpEntity, returnClazz);
    }

    private void refreshTokenIfExpired() {
        if (Objects.isNull(flatIconToken)){
            setAuthorizationToken();
            return;
        }

        long currentTime = Instant.now(clock).getEpochSecond();
        long tokenExpirationTime = flatIconToken.getExpires();
//        long twentyFourHoursInSeconds = 24 * 60 * 60;
        long twentyFourHoursInSeconds = 2;

        if (tokenExpirationTime - currentTime < twentyFourHoursInSeconds) {
            setAuthorizationToken();
        }
    }

    private void setAuthorizationToken() {
        FlatIconToken authToken = postAuthToken();
        this.flatIconToken = authToken;
        this.httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken.getToken());

    }

    private FlatIconToken postAuthToken() {
        URI uri = getAuthenticationURI();
        FlatIconApiKey flatIconApiKeyObject = getFlatIconApiKeyObject();
        ResponseWrapper<String> responseWrapper = super.makeRequest(uri, HttpMethod.POST, String.class, flatIconApiKeyObject);
        FlatIconTokenResponse flatIconTokenResponse = responseWrapper.createRequestedClassFromJson(FlatIconTokenResponse.class);
        return flatIconTokenResponse.getData();
    }


    private URI getAuthenticationURI() {
        return UriComponentsBuilder
                .fromHttpUrl(getApiUrl())
                .pathSegment(version, "app", "authentication")
                .build().toUri();
    }

    private FlatIconApiKey getFlatIconApiKeyObject() {
        return FlatIconApiKey.builder()
                .apikey(getApiKey())
                .build();
    }

}
