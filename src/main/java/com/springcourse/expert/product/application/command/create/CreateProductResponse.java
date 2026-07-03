package com.springcourse.expert.product.application.command.create;

import com.springcourse.expert.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
/*@Data no cubre AllArgsConstructor*/
@AllArgsConstructor
public class CreateProductResponse {

    private Product product;
}
