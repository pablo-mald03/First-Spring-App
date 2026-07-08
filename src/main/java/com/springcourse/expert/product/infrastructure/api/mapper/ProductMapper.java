package com.springcourse.expert.product.infrastructure.api.mapper;

import com.springcourse.expert.category.domain.Category;
import com.springcourse.expert.product.application.command.create.CreateProductRequest;
import com.springcourse.expert.product.application.command.update.UpdateProductRequest;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.infrastructure.api.dto.CreateProductDto;
import com.springcourse.expert.product.infrastructure.api.dto.ProductDto;
import com.springcourse.expert.product.infrastructure.api.dto.ReviewDto;
import com.springcourse.expert.product.infrastructure.api.dto.UpdateProductDto;
import com.springcourse.expert.review.domain.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/*
 * MappingConstants.ComponentModel.SPRING: PERMITE COMUNICARLE A SPRING QUE A LA HORA DE ARRANCAR CREARA UN BEAN
 * E INYECTARA LA IMPLEMENTACION QUE SE VA A CREAR
 *
 * unmappedTargetPolicy: NOTIFICA UN ERROR SI HAY ALGUNA SERIE DE ATRIBUTOS QUE NO PUEDEN COMPILAR O NO SE RELACIONAN
 *
 * */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    /*
     * Source: ProductDto
     * Target: CreateProductRequest
     *
     * Permite la anotacion mapear datos con nombres diferentes
     *
     * @Mapping(target = "id", source = "product_id")
     *
     * Permite ignorar un atributo para que no se mapee
     *
     * @Mapping(target = "id", ignore = true)
     *
     * Permite generar codigo java (OPERACIONES MUY SIMPLES)
     *
     * @Mapping(target = "id", expression = "java(productDto.getId())")
     *
     * LLAMAR A UNA FUNCION QUE REALICE UNA ACCION:
     *
     * @Mapping(target = "id", qualifiedByName = "functionName")
     *
     * default Long mapToId(ProductDto productDto){}
     * */

    CreateProductRequest mapToCreateProductRequest(CreateProductDto productDto);

    UpdateProductRequest mapToUpdateProductRequest(UpdateProductDto updateProductDto);


    @Mapping(target = "provider", source = "productDetail.provider")
    ProductDto mapToProductDto(Product product);

    /*
     * IMPORTANTE DESTACAR QUE PARA QUE MAPSTRUCT PUEDA MAPEAR MAS ENTIDADES DENTRO DE OTRAS ENTIDADES
     * SE DEBEN ESPECIFICAR LOS MAPEOS YA QUE SE AUTORECERENCIA ENTRE METODOS
     *
     * */
    @Mapping(target = "product", ignore = true)
    Review mapToReview(ReviewDto reviewDto);


    /*
     * METODO QUE PERMITE GENERAR OPERACIONES DE MAPEO NECESARIAS
     *
     * */
    default List<String> mapToCategoryNames(List<Category> categories) {
        return categories.stream().map(Category::getName).toList();
    }

}
