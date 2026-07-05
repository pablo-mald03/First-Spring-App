package com.springcourse.expert.review.domain;

import com.springcourse.expert.product.domain.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {

    private Long id;
    private String comment;
    private Integer score;

    private Product product;
}
