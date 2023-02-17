package dev.mjamieson.flexspeak.feature.integration.request;

import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;

import java.net.URI;

public interface RequestApiBindingInterface {
    <T> ResponseWrapper<T> makeRequest(URI uri, HttpMethod httpMethod, Class<T> returnClazz);
    <T> ResponseWrapper<T> makeRequest(URI uri, HttpMethod httpMethod, Class<T> returnClazz,@Nullable Object request);

    void makeAsyncRequest(URI uri, HttpMethod httpMethod, @Nullable Object request);

    String getApiUrl();
    void init();
}
