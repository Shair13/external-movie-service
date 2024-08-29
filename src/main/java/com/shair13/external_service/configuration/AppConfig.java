package com.shair13.external_service.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UrlConfig urlConfig;


//    @Bean
//    @LoadBalanced
//    public RestClient restClient(RestClient.Builder builder){
//        return builder
//                .baseUrl(urlConfig.getDataServiceUrl())
//                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(urlConfig.getDataServiceUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .baseUrl(urlConfig.getDataServiceUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }


    @Bean
    @LoadBalanced
    public RestClient.Builder restClientBuilder(){
        return RestClient.builder()
                .baseUrl(urlConfig.getDataServiceUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    }

    @Bean
    RestClient restClient(RestClient.Builder builder){
        return builder.build();
    }
}
