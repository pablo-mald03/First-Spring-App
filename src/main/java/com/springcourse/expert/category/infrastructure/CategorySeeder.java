package com.springcourse.expert.category.infrastructure;

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
public class CategorySeeder implements CommandLineRunner {

    /*
     * Se vinculan los nuevos repositorios que gestionan las categories del producto
     * */
    private final QueryCategoryRepository queryCategoryRepository;

    private final ResourceLoader resourceLoader;

    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        long count = queryCategoryRepository.count();

        if (count == 0) {

            Resource resource = resourceLoader.getResource("classpath:categories.json");
            List<CategoryEntity> categoryEntities = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
            });
            queryCategoryRepository.saveAll(categoryEntities);
        }


    }
}
