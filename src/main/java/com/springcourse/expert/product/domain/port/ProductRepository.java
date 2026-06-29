package com.springcourse.expert.product.domain.port;

import com.springcourse.expert.product.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void update(Product product);

    void deleteById(Long id);
}
