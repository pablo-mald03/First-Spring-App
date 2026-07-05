package com.springcourse.expert.product.infrastructure.database.mapper;

import com.springcourse.expert.category.domain.Category;
import com.springcourse.expert.category.infrastructure.CategoryEntity;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import com.springcourse.expert.review.domain.Review;
import com.springcourse.expert.review.infrastructure.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

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
}
