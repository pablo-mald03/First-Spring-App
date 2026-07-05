package com.springcourse.expert.productDetail.infrastructure.repository;

import com.springcourse.expert.productDetail.infrastructure.entity.ProductDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryProductDetailRepository extends JpaRepository<ProductDetailEntity, Long> {
}
