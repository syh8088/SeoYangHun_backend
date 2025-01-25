package com.wirebarley.domain.resource.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum HttpMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE")
    ;

    private final String httpMethod;

    HttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public static HttpMethod getByHttpMethod(String httpMethod) {
        return Arrays.stream(HttpMethod.values())
                .filter(data -> data.getHttpMethod().equals(httpMethod))
                .findFirst()
                .orElse(null);
    }
}
