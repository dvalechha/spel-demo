package com.example.spel.config;

import com.example.spel.model.PartnerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DynamicRequestBuilder {
    private final ObjectMapper objectMapper;
    private final ExpressionParser parser = new SpelExpressionParser();

    public DynamicRequestBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Object buildRequest(PartnerConfig config, Object sourceData) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, String> entry : config.getRequestMapping().entrySet()) {
            String key = entry.getKey();
            String spelExpression = entry.getValue();
            Expression exp = parser.parseExpression(spelExpression);
            Object value = exp.getValue(sourceData);
            result.put(key, value);
        }
        return objectMapper.convertValue(result, Object.class);
    }
}
