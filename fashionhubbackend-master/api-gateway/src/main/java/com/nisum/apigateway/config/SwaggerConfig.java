package com.nisum.apigateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    @Primary
    public List<GroupedOpenApi> apis(RouteDefinitionLocator locator) {
        List<GroupedOpenApi> groups = new ArrayList<>();

        // Add User Service API
        groups.add(GroupedOpenApi.builder()
                .group("user-service")
                .pathsToMatch("/user/**", "/test/**")
                .build());

        // Add Product Management Service API
        groups.add(GroupedOpenApi.builder()
                .group("product-management-service")
                .pathsToMatch("/products/**", "/categories/**", "/sellers/**")
                .build());

        return groups;
    }
}
