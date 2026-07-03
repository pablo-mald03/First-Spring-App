package com.springcourse.expert.product.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;


/*
 * REPRESENTA A UNA TABLA
 *
 * PERO NUNA SE UTILIZA @Data porque puede causar problemas de rendimiento y generacion automatica
 * de metodos que se duplican y sobreescriben
 *
 * LO MEJOR ES UTILIZAR:
 *
 * @Getter
 * @Setter
 * @NoArgsConstructo
 * @AllArgsConstructor
 *
 * E INCLUSO @Builder como alternativa viable
 *
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*Indica que representa a una entidad en base de datos*/
@Entity

/*Se le puede dar un nombre para tener un target de la tabla*/
@Table(name = "products")
public class ProductEntity {

    /*Permite poder integrar una llave primaria al objeto*/
    @Id
    /*
     * Permite definir un ID auto incremental
     * */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 300)
    private String description;
    private Double price;
    private String image;
}
