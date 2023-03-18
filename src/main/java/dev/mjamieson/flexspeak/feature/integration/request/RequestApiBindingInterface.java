package dev.mjamieson.flexspeak.feature.integration.request;

import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;

import java.net.URI;
import java.util.concurrent.ExecutorService;

public interface RequestApiBindingInterface {
    <T> ResponseWrapper<T> makeRequest(URI uri, HttpMethod httpMethod, Class<T> returnClazz);
    <T> ResponseWrapper<T> makeRequest(URI uri, HttpMethod httpMethod, Class<T> returnClazz,@Nullable Object request);

    void makeAsyncRequest(URI uri, HttpMethod httpMethod, @Nullable Object request);
    ExecutorService getExecutorService();
    String getApiUrl();
    void init();
}
