package com.gaf.anagram.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {
    @Autowired
    Environment env;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(Long.parseLong(env.getProperty("rest.connect.timeout"))))
                .setReadTimeout(Duration.ofSeconds(Long.parseLong(env.getProperty("rest.read.timeout"))))
                .build();
    }
}