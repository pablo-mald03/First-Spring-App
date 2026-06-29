package com.springcourse.expert.product.application.query.getbyid;

import com.springcourse.expert.common.mediator.RequestHandler;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.domain.exception.ProductNotFoundException;
import com.springcourse.expert.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GetProductByIdHandler implements RequestHandler<GetProductByIdRequest, GetProductByIdResponse> {

    private final ProductRepository productRepository;

    @Override
    public GetProductByIdResponse handle(GetProductByIdRequest request) {

        log.info("Getting product with id {}", request.getId());

        Product product = productRepository.findById(request.getId()).orElseThrow(() -> new ProductNotFoundException(request.getId()));
        
        log.info("Found product with id {}", product.getId());
        return new GetProductByIdResponse(product);
    }

    /*
     * RETORNA EL NOMBRE DE LA CLASE
     * */
    @Override
    public Class<GetProductByIdRequest> getRequestType() {
        return GetProductByIdRequest.class;
    }
}
