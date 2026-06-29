package com.springcourse.expert.product.application.query.getbyid;

import com.springcourse.expert.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/*
 * LAS ACCIONES QUE RETORNAN UN TIPO DE DATO
 *
 * VAN DENTRO DEL PAQUETE DE query
 *
 *
 * SIEMPRE SE RECOMIENDA SEGUIR EL ESTANDAR
 *
 * NameObjectActionResponse
 *
 * REPRESENTA A UN OBJETO QUE SE RETORNARA
 *
 * */
@Data
@Builder
@AllArgsConstructor
public class GetProductByIdResponse {

    private Product product;
}
