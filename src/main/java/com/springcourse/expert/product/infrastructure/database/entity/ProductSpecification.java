package com.springcourse.expert.product.infrastructure.database.entity;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    /*
     * Es lo que permite escribir consultas personalizadas con JPA
     *
     * criteriaBuilder: Permite especificar una funcion en la que se cree un criterio sobre cierto campo con una consulta aproximada
     *
     * Se pueden agregar comprobaciones si el criterio es nulo
     * */
    public static Specification<ProductEntity> byName(String name) {
        return (root, query, criteriaBuilder) -> name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<ProductEntity> byDescription(String description) {
        return (root, query, criteriaBuilder) -> description == null ? null : criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    /*
     * EXISTEN VARIAS FUNCIONES DEL CRITERIALBUILDER Y ESTO LO QUE PERMITE ES PODER EDPECIFICAR EL FILTRO EN EL QUE SE VA A BASAR
     *
     * criteriaBuilder.between(root.get("price")
     * criteriaBuilder.like(root.get("price")
     * criteriaBuilder.equals(root.get("price")
     *
     * */
    public static Specification<ProductEntity> byPrice(Double priceMin, Double priceMax) {
        return (root, query, criteriaBuilder) -> priceMin == null || priceMax == null ? null : criteriaBuilder.between(root.get("price"), priceMin, priceMax);
    }
}
