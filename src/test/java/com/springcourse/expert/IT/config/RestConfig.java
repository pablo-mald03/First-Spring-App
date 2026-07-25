package com.springcourse.expert.IT.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/*
 * Clase que permite poder testear con templates para URL con seguridad
 * */
@TestConfiguration
public class RestConfig {


    @Value("${jwt.token}")
    private String token;

    @Bean
    public TestRestTemplate restTemplate() {
        return new TestRestTemplate(
//                new RestTemplateBuilder()
//                        .defaultHeader("Authorization", "Bearer")
//                        .connectionTimeout(Duration.ofSeconds(10))
//                        .rootUri("http://localhost:8080")
        );
    }
}
