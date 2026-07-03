package com.springcourse.expert.product.infrastructure.api;

import com.springcourse.expert.common.domain.PaginationResult;
import com.springcourse.expert.product.infrastructure.api.dto.CreateProductDto;
import com.springcourse.expert.product.infrastructure.api.dto.ProductDto;
import com.springcourse.expert.product.infrastructure.api.dto.UpdateProductDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductRestController {

    ResponseEntity<PaginationResult<ProductDto>> getAllProducts(int pageNumber, int pageSize, String sortBy, String direction, String name, String description, Double priceMin, Double priceMax);

    ResponseEntity<ProductDto> getProductById(@PathVariable Long id);

    ResponseEntity<Void> saveProduct(@RequestBody @Valid CreateProductDto product);

    ResponseEntity<Void> updateProduct(@RequestBody @Valid UpdateProductDto product);

    ResponseEntity<Void> deleteProduct(@PathVariable Long id);
}
