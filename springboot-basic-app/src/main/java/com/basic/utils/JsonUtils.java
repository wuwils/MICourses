package com.basic.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJsonString(Object object) {
        return toJsonString(object, false);
    }

    public static String toJsonString(Object object, boolean pretty) {
        try {
            if (pretty) {
                return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            } else {
                return OBJECT_MAPPER.writeValueAsString(object);
            }
        } catch (Throwable t) {
            throw wrapRuntimeException(t);
        }
    }

    public static <T> T toObject(String jsonStr, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Throwable t) {
            throw wrapRuntimeException(t);
        }
    }

    public static Map<String, Object> toMap(String jsonStr) {
        return OBJECT_MAPPER.convertValue(toJsonNode(jsonStr), new TypeReference<Map<String, Object>>(){});
    }

    public static JsonNode toJsonNode(String jsonStr) {
        try {
            return OBJECT_MAPPER.readTree(jsonStr);
        } catch (Throwable t) {
            throw wrapRuntimeException(t);
        }
    }

    public static <T> T toObject(Map<String, Object> map, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(map, clazz);
    }
    public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            return OBJECT_MAPPER.readValue(jsonStr, javaType);
        } catch (Throwable t) {
            throw wrapRuntimeException(t);
        }
    }

    public static <T> List<T> toList(List<Object> list, Class<T> clazz) {
        CollectionType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        return OBJECT_MAPPER.convertValue(list, listType);
    }

    public static boolean verifyJson(String jsonStr) {
        try {
            OBJECT_MAPPER.readTree(jsonStr);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    static RuntimeException wrapRuntimeException(Throwable t) {
        return t instanceof  RuntimeException ? (RuntimeException) t : new RuntimeException(t);
    }
}
