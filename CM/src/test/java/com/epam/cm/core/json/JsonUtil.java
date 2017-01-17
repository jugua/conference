package com.epam.cm.core.json;

import com.epam.cm.dto.restDto.TalksRestDTO;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

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

    public static String toJSON(TalksRestDTO talksRestDTO) throws IOException {

        return new ObjectMapper().writeValueAsString(talksRestDTO);

    }
}
