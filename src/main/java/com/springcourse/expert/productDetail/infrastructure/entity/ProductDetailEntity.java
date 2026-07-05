package com.springcourse.expert.productDetail.infrastructure.entity;

import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

/*Clase que representa a una entidad de detalles del producto*/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product_detail")
public class ProductDetailEntity {

    @Id
    /*
     * Representacion autoincremental
     * */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String specifications;
    private String warranty;
    private String provider;


    /*
     * Se puede referenciar directamente
     * para hacer referencia al campo
     * @JoinColumn(name = "productDetailEntity")
     *
     * */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_detail_id")
    private ProductEntity product;
}
