package dev.mjamieson.flexspeak.integration.request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public abstract class RequestAPIBindingBase implements RequestApiBindingInterface {
    protected HttpHeaders httpHeaders;

    @Override
    public void init() {
        this.httpHeaders = setHttpHeaders();
    }

    @Override
    public <T> ResponseWrapper<T> makeRequest(URI uri, HttpMethod httpMethod, Class<T> returnClazz) {
        return makeRequest(uri, httpMethod, returnClazz, null);
    }
    @Override
    public <T> ResponseWrapper<T> makeRequest(URI uri, HttpMethod httpMethod, Class<T> returnClazz, @Nullable Object request) {
        HttpEntity httpEntity = new HttpEntity(request, httpHeaders);
            return RequestApi.makeRequest(uri, httpMethod, httpEntity, returnClazz);
    }

    @Override
    public void makeAsyncRequest(URI uri, HttpMethod httpMethod, @Nullable Object request) {
        HttpEntity httpEntity = new HttpEntity(request, httpHeaders);
        CompletableFuture.runAsync(() -> RequestApi.makeRequest(uri, httpMethod, httpEntity, String.class));
    }


    public abstract String getApiUrl();


    protected HttpHeaders setHttpHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }
}
