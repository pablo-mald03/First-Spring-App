package com.springcourse.expert.product.infrastructure.api;

import com.springcourse.expert.product.infrastructure.api.dto.CreateProductDto;
import com.springcourse.expert.product.infrastructure.api.dto.ProductDto;
import com.springcourse.expert.product.infrastructure.api.dto.UpdateProductDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductRestController {

    ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false) String pageSize);

    ResponseEntity<ProductDto> getProductById(@PathVariable Long id);

    ResponseEntity<Void> saveProduct(@RequestBody @Valid CreateProductDto product);

    ResponseEntity<Void> updateProduct(@RequestBody @Valid UpdateProductDto product);

    ResponseEntity<Void> deleteProduct(@PathVariable Long id);
}
