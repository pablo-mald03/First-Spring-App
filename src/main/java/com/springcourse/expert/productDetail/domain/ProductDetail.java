package com.springcourse.expert.productDetail.domain;

import com.springcourse.expert.product.domain.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetail {

    private Long id;
    private String specifications;
    private String warranty;
    private String provider;

    /*
     * NO SE DEFINE LA ENTIDAD DIRECTAMENTE PORQUE SE ESTA DEFINIENDO LA CAPA DE ARQUITECTURA HEXAGONAL
     * YA QUE DESCRIBE AL PUERTO EN EL QUE SE VA A RELACIONAR EL OBJETO
     * */
    private Product product;
}
