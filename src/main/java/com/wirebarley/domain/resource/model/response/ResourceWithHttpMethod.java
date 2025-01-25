package com.wirebarley.domain.resource.model.response;

import com.wirebarley.domain.resource.enums.HttpMethod;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"resourceName", "httpMethod"})
public class ResourceWithHttpMethod {

    private String resourceName;
    private HttpMethod httpMethod;

    @Builder
    private ResourceWithHttpMethod(String resourceName, HttpMethod httpMethod) {
        this.resourceName = resourceName;
        this.httpMethod = httpMethod;
    }

    public static ResourceWithHttpMethod of(String resourceName, HttpMethod httpMethod) {
        return new ResourceWithHttpMethod(resourceName, httpMethod);
    }
}
