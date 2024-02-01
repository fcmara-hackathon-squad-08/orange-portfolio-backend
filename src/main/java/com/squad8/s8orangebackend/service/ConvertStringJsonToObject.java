package com.squad8.s8orangebackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConvertStringJsonToObject {

    private final ObjectMapper objectMapper;

    public ConvertStringJsonToObject(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T deserialize(String json, Class<T> targetType) throws IOException {
        return objectMapper.readValue(json, targetType);
    }

}