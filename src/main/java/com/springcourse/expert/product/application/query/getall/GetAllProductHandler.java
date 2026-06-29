package com.springcourse.expert.product.application.query.getall;

import com.springcourse.expert.common.mediator.RequestHandler;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.domain.port.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GetAllProductHandler implements RequestHandler<GetAllProductRequest, GetAllProductResponse> {

    private final ProductRepository productRepository;

    @Override
    public GetAllProductResponse handle(GetAllProductRequest request) {

        log.info("Getting all products");

        List<Product> products = productRepository.findAll();
        log.info("Found {} products", products.size());
        return new GetAllProductResponse(products);
    }

    /*
     * RETORNA EL NOMBRE DE LA CLASE
     * */
    @Override
    public Class<GetAllProductRequest> getRequestType() {
        return GetAllProductRequest.class;
    }
}
