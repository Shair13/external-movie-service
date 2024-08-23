package com.shair13.external_service.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "movie.service")
public class UrlConfig {
    private String dataServiceUrl;
    private String swaggerUrl;
    private String keycloakConfigUrl;
}
