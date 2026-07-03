package com.springcourse.expert.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Entidad que representa un filtro del producto
 *
 * REPRESENTA UN ADAPTADOR QUE RECIBE CIERTOS PARAMETROS DE UN OBJETO CON MENOS ATRIBUTOS Y CIERTO FILTRO
 *
 * */
@Data
@AllArgsConstructor
public class ProductFilter {

    private String name;
    private String description;
    private Double priceMin;
    private Double priceMax;
}
