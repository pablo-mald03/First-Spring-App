package com.springcourse.expert.product.domain.entity;

import com.springcourse.expert.category.domain.Category;
import com.springcourse.expert.productDetail.domain.ProductDetail;
import com.springcourse.expert.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
/*@Data es mala practica en entitys
 *
 * EN ESTE CASO SE PUEDEN REPETIR CLASES Y PARECE CODIGO REPETITIVO
 *
 * PERO EN UN SISTEMA GRANDE GENERAR ESE INTERMEDIARIO ANTES DE LLEGAR A UNA @Entity
 * ES IMPORTANTE PORQUE SON INTERMEDIOS DE COMUNICACION CON OTRAS FUNCIONES DE LA API
 *
 * */
@Data
@Builder
public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;

    private ProductDetail productDetail;

    private List<Review> reviews;

    private List<Category> categories;
}
