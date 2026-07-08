package com.springcourse.expert.product.infrastructure.database.mapper;

import com.springcourse.expert.category.domain.Category;
import com.springcourse.expert.category.infrastructure.CategoryEntity;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import com.springcourse.expert.review.domain.Review;
import com.springcourse.expert.review.infrastructure.ReviewEntity;
import org.mapstruct.*;

/*
 * Los mappers de las entitys se especializan directaemente en database
 * porque son mappers que sirven directamente como la capa de persistencia
 *
 * */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductEntityMapper {


    /*
     * SE EVITA QUE SE HAGA UN BUCLE DE MAPEO PORQUE CADA UNO HACE
     * REFERENCIA DEL OTRO ASI QUE SOLO SE BUSCA LA REFERENCIA NECESARIA
     * Y SE IGNORA EL MAPEO LITERAL
     * */
    @Mapping(target = "productDetail.product", ignore = true)
    ProductEntity mapToProductEntity(Product product);


    @Mapping(target = "productDetail.product", ignore = true)
    Product mapToProduct(ProductEntity productEntity);

    @Mapping(target = "product", ignore = true)
    Review mapToReview(ReviewEntity reviewEntity);

    @Mapping(target = "product", ignore = true)
    ReviewEntity mapToReviewEntity(Review review);

    @Mapping(target = "products", ignore = true)
    Category mapToCategory(CategoryEntity categoryEntity);

    @Mapping(target = "products", ignore = true)
    CategoryEntity mapToCategoryEntity(Category category);

    /*
     * Esta anotacion @AfterMapping
     *
     * Indica un proceso que se llama cuando ya esta mapeada una entidad
     *
     * MUY IMPORTANTE REALIZARLO PORQUE REALMENTE AL IGNORAR EL product
     *
     * ESTO PERMITE QUE NO SE MAPEEN LAS REVIEWS O LAS LISTAS INTERNAS Y SIMPLEMENTE SE PERDERIAN
     * TODAS
     *
     * POR LO TANTO HAY QUE CARGAR LAS QUE YA ESTAN EVITANDO QUE SE MODIFIQUEN
     *
     * SI EN DADO CASO NO SE CARGA UN CAMPO HAY QUE ESTAR SEGURO QUE NO SE AFECTE ALGO DIRECTAMENTE A CAMPOS
     * YA QUE SI SE NULEAN DATOS ESTAS RELACIONES SE ELIMINARAN
     *
     * ESTO OCURRE MUCHO EN OneToMany ya que este solo tiene el target hacia una tabla
     *
     * */
    @AfterMapping
    default void linkReviews(@MappingTarget ProductEntity productEntity) {
        if (productEntity.getReviews() != null) {
            productEntity.getReviews().forEach(review -> review.setProduct(productEntity));
        }
    }
}
