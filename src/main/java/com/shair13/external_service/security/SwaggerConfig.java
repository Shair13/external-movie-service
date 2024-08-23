package com.shair13.external_service.security;

import com.shair13.external_service.configuration.UrlConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final UrlConfig urlConfig;
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization"))
                        .addSecuritySchemes("Keycloak",
                                new SecurityScheme()
                                        .name("Keycloak")
                                        .openIdConnectUrl(urlConfig.getKeycloakConfigUrl())
                                        .scheme("bearer")
                                        .type(SecurityScheme.Type.OPENIDCONNECT)
                                        .in(SecurityScheme.In.HEADER)))
                .addSecurityItem(
                        new SecurityRequirement().addList("bearer-jwt").addList("Keycloak"))
                .servers(List.of(
                        new Server().url(urlConfig.getSwaggerUrl()).description("movies api")
                ));
    }
}