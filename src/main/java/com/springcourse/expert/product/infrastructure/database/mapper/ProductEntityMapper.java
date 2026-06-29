package com.springcourse.expert.product.infrastructure.database.mapper;

import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/*
 * Los mappers de las entitys se especializan directaemente en database
 * porque son mappers que sirven directamente como la capa de persistencia
 *
 * */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductEntityMapper {

    ProductEntity mapToProductEntity(Product product);

    Product mapToProduct(ProductEntity productEntity);
}
