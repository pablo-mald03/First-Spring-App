package com.springcourse.expert.productDetail.infrastructure.seeder;

import com.springcourse.expert.productDetail.infrastructure.entity.ProductDetailEntity;
import com.springcourse.expert.productDetail.infrastructure.repository.QueryProductDetailRepository;
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
public class ProductDetailSeeder implements CommandLineRunner {

    /*
     * Se vinculan los nuevos repositorios que gestionan los details del producto
     * */
    private final QueryProductDetailRepository productDetailRepository;

    private final ResourceLoader resourceLoader;

    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        long count = productDetailRepository.count();

        if (count == 0) {

            Resource resource = resourceLoader.getResource("classpath:products_details.json");
            List<ProductDetailEntity> productsDetailtEntities = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
            });


            productDetailRepository.saveAll(productsDetailtEntities);
        }


    }
}
