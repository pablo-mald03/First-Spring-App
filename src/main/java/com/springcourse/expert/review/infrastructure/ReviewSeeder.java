package com.springcourse.expert.review.infrastructure;

import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import com.springcourse.expert.product.infrastructure.database.repository.QueryProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(4)
public class ReviewSeeder implements CommandLineRunner {

    private final QueryProductRepository queryProductRepository;

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

            List<ReviewSeed> reviews = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<>() {
                    }
            );
            
            List<ReviewEntity> entities = reviews.stream()
                    .map(review -> {

                        ProductEntity product = queryProductRepository
                                .findById(review.getProductId())
                                .orElseThrow();

                        ReviewEntity entity = new ReviewEntity();

                        entity.setComment(review.getComment());
                        entity.setScore(review.getScore());
                        entity.setProduct(product);

                        return entity;
                    })
                    .toList();

            queryReviewRepository.saveAll(entities);
        }


    }
}
