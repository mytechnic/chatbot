package com.example.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

public class ObjectMapperUtils {
    private final static ObjectMapper objectMapper = getDefaultObjectMapper();

    public static byte[] serializeAsBytes(Object obj, byte[] defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        try {
            return serializeAsBytes(obj);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static byte[] serializeAsBytes(Object obj) throws JsonProcessingException {
        if (obj == null) {
            return null;
        }

        return objectMapper.writeValueAsBytes(obj);
    }

    public static String serializeAsString(Object obj, String defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        try {
            return serializeAsString(obj);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String serializeAsString(Object obj) throws JsonProcessingException {
        if (obj == null) {
            return null;
        }

        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T deserialize(byte[] json, Class<T> valueType, T defaultValue) {
        if (json.length == 0) {
            return defaultValue;
        }

        try {
            return deserialize(json, valueType);
        } catch (IOException e) {
            return defaultValue;
        }
    }

    public static <T> T deserialize(byte[] json, Class<T> valueType) throws IOException {
        if (json.length == 0) {
            return null;
        }

        return objectMapper.readValue(json, valueType);
    }

    public static <T> T deserialize(String json, Class<T> valueTyp, T defaultValue) {
        if (ObjectUtils.isEmpty(json)) {
            return defaultValue;
        }

        try {
            return deserialize(json, valueTyp);
        } catch (JsonProcessingException e) {
            return defaultValue;
        }
    }

    public static <T> T deserialize(String json, Class<T> valueType) throws JsonProcessingException {
        if (ObjectUtils.isEmpty(json)) {
            return null;
        }

        return objectMapper.readValue(json, valueType);
    }

    public static ObjectMapper getDefaultObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.ALWAYS, JsonInclude.Include.NON_NULL));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
