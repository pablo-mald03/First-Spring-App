package com.springcourse.expert.product.infrastructure.database.entity;

import com.springcourse.expert.category.infrastructure.CategoryEntity;
import com.springcourse.expert.productDetail.infrastructure.entity.ProductDetailEntity;
import com.springcourse.expert.review.infrastructure.ReviewEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


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

    /*
     * @OneToOne: ESPECIFICA UNA RELACION UNO A UNO DE SQL
     *
     * cascade: REPRESENTA LAS OPERACIONES EN CASCADA YA SEA QUE SE PUEDAN ELIMINAR TODAS LAS RELACIONES DE UN SOLO:
     *
     *CascadeType.ALL: Elimina todo en cadena o modifica todo en cadena
     *
     * SIEMPRE HAY QUE MANTENER LA CARDINALIDAD DE RELACIONES
     *
     * fetch: Hace referencia a como se va a cargar la informacion y se va a obtener de la base de datos
     * existen dos tipos:
     *
     * FetchType.LAZY: Cuando se busque en el repositorio y se obtenga la informacion SOLO CUANDO SE NECESITE
     * es decir que hay que llamar a una query especifica para poder obtener la informacion de la relacion
     *
     * FetchType.EAGER: Una vez que con el repositorio se obtiene a la entidad de producto
     * se toma directamente en la query la relacion y todos los registros guardados
     *
     *
     * DEFAULT O SIN FETCHTYPE SE PONE EAGGER
     *
     * */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_detail_id")
    private ProductDetailEntity productDetail;


    /*
     * Se especifica como se iran relacionando los mapeos de las entidades de reviews
     * respecto a cada producto.
     *
     * ES DECIR SE ESPECIFICA QUE SE TENDRAN MUCHAS ENTIDADES DE REVIEWS
     * POR CADA PRODUCTO.
     *
     * REPRESENTAN LA CARDINALIDAD RESPECTIVA
     *
     * */
    @OneToMany(mappedBy = "product")
    private List<ReviewEntity> reviews = new ArrayList<>();

    @ManyToMany
    /*
     * SE USA JOIN TABLE PORQUE PRECISAMENTE ESTO FUNCIONA COMO UNA TABLA INTERMEDIA
     * QUE SE CREA PARA PODER TENER UNA RELACION DE MUCHOS A MUCHOS
     *
     * POR LO TANTO POR ESO SE ESPECIFICA LA TABLA como @JoinTable
     *
     * name = NOMBRE DE LA TABLA INTERMEDIA
     * joinColums = columna relacion
     * inverseJoinColumns = columna hacia la que se enlazan las tablas
     *
     *  */
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryEntity> categories = new ArrayList<>();
}
