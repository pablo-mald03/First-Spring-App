package com.springcourse.expert.product.application.query.getall;

import com.springcourse.expert.common.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Si es un findAll no requiere ningun parametro solo representa el request
 * */
@Data
@AllArgsConstructor
public class GetAllProductRequest implements Request<GetAllProductResponse> {
}
