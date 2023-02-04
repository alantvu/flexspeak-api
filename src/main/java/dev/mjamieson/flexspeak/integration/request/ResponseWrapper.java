package dev.mjamieson.flexspeak.integration.request;


import dev.mjamieson.flexspeak.integration.json.ExternalJson;

import java.util.List;

public class ResponseWrapper<T> {
    private T response;

    public T getResponse () { return response; }
    public void setResponse(T response) {
        this.response = response;
    }

    public <A> List<A> createRequestedClassListFromJson(Class<A> clazz){
        return ExternalJson.createRequestClassListFromJsonString(response, clazz);
    }
    public <A> A createRequestedClassFromJson(Class<A> clazz){
        return ExternalJson.createRequestClassObjectFromJsonString(response, clazz);
    }

}