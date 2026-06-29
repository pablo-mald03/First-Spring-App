package com.springcourse.expert.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
}
