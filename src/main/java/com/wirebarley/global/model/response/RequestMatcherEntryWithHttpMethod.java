package com.wirebarley.global.model.response;

import com.wirebarley.domain.resource.enums.HttpMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;

@Getter
@NoArgsConstructor
public class RequestMatcherEntryWithHttpMethod {

    private RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> requestMatcherEntry;
    private HttpMethod httpMethod;

    @Builder
    private RequestMatcherEntryWithHttpMethod(RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> requestMatcherEntry, HttpMethod httpMethod) {
        this.requestMatcherEntry = requestMatcherEntry;
        this.httpMethod = httpMethod;
    }

    public static RequestMatcherEntryWithHttpMethod of(
            RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> requestMatcherEntry,
            HttpMethod httpMethod
    ) {
        return RequestMatcherEntryWithHttpMethod.builder()
                .requestMatcherEntry(requestMatcherEntry)
                .httpMethod(httpMethod)
                .build();
    }
}
