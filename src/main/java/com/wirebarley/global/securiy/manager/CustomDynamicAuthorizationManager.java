package com.wirebarley.global.securiy.manager;

import com.wirebarley.domain.resource.enums.HttpMethod;
import com.wirebarley.domain.resource.model.response.ResourceWithHttpMethod;
import com.wirebarley.domain.resource.repository.ResourceRepository;
import com.wirebarley.global.model.response.RequestMatcherEntryWithHttpMethod;
import com.wirebarley.global.securiy.mapper.PersistentUrlRoleMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomDynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    List<RequestMatcherEntryWithHttpMethod> mappings;

    private static final AuthorizationDecision ACCESS = new AuthorizationDecision(true);
    private static final AuthorizationDecision DENY = new AuthorizationDecision(false);

    private final HandlerMappingIntrospector handlerMappingIntrospector;
    private final ResourceRepository resourceRepository;

    private final RoleHierarchyImpl roleHierarchy;
    private DynamicAuthorizationService dynamicAuthorizationService;

    @PostConstruct
    public void mapping() {

        this.dynamicAuthorizationService = new DynamicAuthorizationService(new PersistentUrlRoleMapper(resourceRepository));
        this.setMapping();
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext request) {

        HttpMethod requestHttpMethod = HttpMethod.getByHttpMethod(request.getRequest().getMethod());

        for (RequestMatcherEntryWithHttpMethod mapping : this.mappings) {

            RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> requestMatcherEntry = mapping.getRequestMatcherEntry();
            HttpMethod httpMethod = mapping.getHttpMethod();
            if (!requestHttpMethod.equals(httpMethod)) {
                continue;
            }

            RequestMatcher matcher = requestMatcherEntry.getRequestMatcher();
            RequestMatcher.MatchResult matchResult = matcher.matcher(request.getRequest());

            if (matchResult.isMatch()) {
                AuthorizationManager<RequestAuthorizationContext> manager = requestMatcherEntry.getEntry();
                return manager.check(
                        authentication,
                        new RequestAuthorizationContext(request.getRequest(), matchResult.getVariables())
                );
            }
        }

        return ACCESS;
    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    private AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager(String role) {

        if (role.startsWith("ROLE")) {
            AuthorityAuthorizationManager<RequestAuthorizationContext> authorizationManager = AuthorityAuthorizationManager.hasAuthority(role);
            authorizationManager.setRoleHierarchy(roleHierarchy);

            return authorizationManager;
        }
        else {
            DefaultHttpSecurityExpressionHandler handler = new DefaultHttpSecurityExpressionHandler();
            handler.setRoleHierarchy(roleHierarchy);

            WebExpressionAuthorizationManager authorizationManager = new WebExpressionAuthorizationManager(role);
            authorizationManager.setExpressionHandler(handler);

            return authorizationManager; // 표현식 사용
        }
    }

    public synchronized void reload() {

        this.mappings.clear();
        this.setMapping();
    }

    private void setMapping() {

        this.mappings = this.dynamicAuthorizationService.selectUrlRoleMappings()
                .entrySet()
                .stream()
                .map(entry -> {

                    ResourceWithHttpMethod resourceWithHttpMethod = entry.getKey();

                    MvcRequestMatcher mvcRequestMatcher = new MvcRequestMatcher(this.handlerMappingIntrospector, resourceWithHttpMethod.getResourceName());
                    AuthorizationManager<RequestAuthorizationContext> requestAuthorizationContextAuthorizationManager
                            = this.customAuthorizationManager(entry.getValue());

                    return RequestMatcherEntryWithHttpMethod.of(
                            new RequestMatcherEntry<>(mvcRequestMatcher, requestAuthorizationContextAuthorizationManager),
                            resourceWithHttpMethod.getHttpMethod()
                    );
                })
                .collect(Collectors.toList());
    }
}
