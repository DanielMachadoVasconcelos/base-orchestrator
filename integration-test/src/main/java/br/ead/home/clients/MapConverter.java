package br.ead.home.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public final class MapConverter {

    private MapConverter() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setSerializationInclusion(NON_NULL)
            .disable(WRITE_DATES_AS_TIMESTAMPS)
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    public static <T> T toClass(Object fixture, Class<T> clazz) {
        try {
            String json = MAPPER.writeValueAsString(fixture);
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not convert fixture to type", e);
        }
    }

    public static Map<String, String> jsonToMap(String json) {
        try {
            // noinspection unchecked
            return MAPPER.readValue(json, Map.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not convert JSON to map", e);
        }
    }

    public static Map<String, String> toMap(Object object) {
        try {
            String json = MAPPER.writeValueAsString(object);
            // noinspection unchecked
            return MAPPER.readValue(json, Map.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not convert object to map", e);
        }
    }

    public static List<Object> toList(Object object) {
        try {
            String json = MAPPER.writeValueAsString(object);
            // noinspection unchecked
            return MAPPER.readValue(json, List.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not convert object to map", e);
        }
    }

    public static String asJson(Object request) {
        try {
            return MAPPER.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize request.", e);
        }
    }

}
