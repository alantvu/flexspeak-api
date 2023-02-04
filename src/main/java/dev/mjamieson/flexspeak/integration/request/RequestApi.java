package dev.mjamieson.flexspeak.integration.request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public abstract class RequestApi {
    /**Pass a String as a response clazz and then convert it to Json.*/

    public static <T> ResponseWrapper<T> makeRequest(URI uri, HttpMethod httpMethod, HttpEntity httpEntity, Class<T> returnClazz) {

        RestTemplate restTemplate = new RestTemplate();
        try{

        ResponseEntity<T> response = restTemplate.exchange(
                uri,
                httpMethod,
                httpEntity,
                returnClazz);

            ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
            responseWrapper.setResponse(response.getBody());

            return responseWrapper;
        }catch(HttpServerErrorException e){
//            e.getResponseBodyAsString();
            throw new RuntimeException(e.getResponseBodyAsString());

        }

    }
}