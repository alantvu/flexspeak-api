package dev.mjamieson.flexspeak.integration.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ExternalJsonLowLevel {

    protected static ObjectMapper objectMapper = getDefaultObjectMapper();

    protected static ObjectMapper getDefaultObjectMapper(){
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return defaultObjectMapper;
    }
    @SneakyThrows
    public static JsonNode parse(String src){
        try {
            return objectMapper.readTree(src);
        } catch (Exception e) {
            throw e;
        }
    }
    @SneakyThrows
    public static <A> A fromJson(JsonNode node, Class<A> clazz){
        try {
            return objectMapper.treeToValue(node, clazz);
        } catch (JsonProcessingException e) {
            throw e;
        }
    }

    @SneakyThrows
    protected static String convertMapToJsonString(Map elements) {
        try {
            String json = objectMapper.writeValueAsString(elements);

            return json;
        } catch (JsonProcessingException e) {
            throw e;
        }
    }

    protected static List<JsonNode> jsonNodesFromJsonNode(JsonNode jsonNode) {
        List list = (List) fromJson(jsonNode,List.class);
        List<String> jsonList = jsonListFromList(list);
        List<JsonNode> jsonNodes = jsonNodesFromJsonList(jsonList);
        return jsonNodes;
    }

    protected static  List<JsonNode> jsonNodesFromJsonList(List<String> jsonList){
        return (List) jsonList.stream().map(obj -> parse((String) obj)).collect(Collectors.toList());
    }


    protected static List<String> jsonListFromList(List list){
        return (List) list.stream().map(obj -> convertMapToJsonString((Map) obj)).collect(Collectors.toList());
    }


    protected static List<JsonNode> jsonNodesFromListMaps(List<Map> maps){
        List<JsonNode> jsonNodes = new ArrayList<>();
        maps.forEach(map -> {
            String jsonString = convertMapToJsonString(map);
            JsonNode jsonNode = parse(jsonString);
            jsonNodes.add(jsonNode);
        });
        return jsonNodes;
    }

    protected static JsonNode convertMapToStringThenToJsonNode(Map map){
        String jsonString = convertMapToJsonString(map);
        return parse(jsonString);
    }


    protected static <A> List<A> genericListFromJsonNodes(List<JsonNode> jsonNodes, Class<A> clazz){
        return (List<A>) jsonNodes.stream().map(obj -> (A) fromJson(obj,clazz)).collect(Collectors.toList());
    }
    protected static <A> A genericFromJsonNode(JsonNode jsonNode, Class<A> clazz){
        return fromJson(jsonNode,clazz);
    }
}

