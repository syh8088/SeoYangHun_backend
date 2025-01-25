package com.wirebarley.domain.resource.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ResourceType {

    URL("URL"),
    ;

    private final String resourceType;

    ResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public static ResourceType getByResourceType(String resourceType) {
        return Arrays.stream(ResourceType.values())
                .filter(data -> data.getResourceType().equals(resourceType))
                .findFirst()
                .orElse(null);
    }
}
