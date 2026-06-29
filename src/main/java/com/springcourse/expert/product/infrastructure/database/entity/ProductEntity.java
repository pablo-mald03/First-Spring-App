package com.springcourse.expert.product.infrastructure.database.entity;

import lombok.Data;


/*
 * REPRESENTA A UNA TABLA
 *
 * */
@Data
public class ProductEntity {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;
}
