package com.springcourse.expert.product.application.query.getall;

import com.springcourse.expert.common.application.mediator.Request;
import com.springcourse.expert.common.domain.PaginationQuery;
import com.springcourse.expert.product.domain.entity.ProductFilter;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Si es un findAll no requiere ningun parametro solo representa el request
 * */
@Data
@AllArgsConstructor
public class GetAllProductRequest implements Request<GetAllProductResponse> {

    /*
     * SE DEFINE UN CAMPO DE PAGINACION
     * */
    PaginationQuery paginationQuery;

    /*
     * Filtro opcional
     * */
    ProductFilter productFilter;
}
