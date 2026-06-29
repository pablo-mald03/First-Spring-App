package com.springcourse.expert.product.application.query.getbyid;

import com.springcourse.expert.common.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

/*Tener un constructor con todos los parametros porque @Data NO LO GENERA*/
@AllArgsConstructor
public class GetProductByIdRequest implements Request<GetProductByIdResponse> {
    private Long id;
}
