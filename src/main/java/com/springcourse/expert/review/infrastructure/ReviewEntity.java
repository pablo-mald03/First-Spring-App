package com.springcourse.expert.review.infrastructure;

import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "reviews")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Integer score;

    /*
     * Representa una relacion de uno a muchos
     * */
    @ManyToOne

    /*
     * Representa a la columna de foreing key mediante la que se va a relacionar la review
     * es decir que se especifica cual sera este campo
     * */
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
