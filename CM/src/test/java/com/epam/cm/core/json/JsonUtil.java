package com.epam.cm.core.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


public final class JsonUtil {

    private JsonUtil() {
    }

    public static <T> T fromJSON(final TypeReference<T> type, final String jsonObject) {
        T data;

        try {
            data = new ObjectMapper().readValue(jsonObject, type);
        } catch (Exception ignore) {
            data = null;
        }

        return data;
    }
}
