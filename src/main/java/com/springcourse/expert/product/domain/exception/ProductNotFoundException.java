package com.springcourse.expert.product.domain.exception;

/*
 * Excepcion propia que permite indicar la entidad que no fue encontrada
 * */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("The product with ID: " + id + " was not found");
    }
}
