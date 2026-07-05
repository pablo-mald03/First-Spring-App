package com.springcourse.expert.review.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewSeeder implements CommandLineRunner {

    /*
     * Se vinculan los nuevos repositorios que gestionan los details del producto
     * */
    private final QueryReviewRepository queryReviewRepository;

    private final ResourceLoader resourceLoader;

    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        long count = queryReviewRepository.count();

        if (count == 0) {

            Resource resource = resourceLoader.getResource("classpath:reviews.json");
            List<ReviewEntity> reviewEntities = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
            });
            queryReviewRepository.saveAll(reviewEntities);
        }


    }
}
