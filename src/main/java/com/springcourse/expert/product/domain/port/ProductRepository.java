package com.springcourse.expert.product.domain.port;

import com.springcourse.expert.common.domain.PaginationQuery;
import com.springcourse.expert.common.domain.PaginationResult;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.domain.entity.ProductFilter;

import java.util.Optional;

/*LA INTERFACE REPRESENTA EL PUERTO QUE CONECTA AL REPOSITORIO*/
public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    PaginationResult<Product> findAll(PaginationQuery paginationQuery, ProductFilter productFilter);

    Product update(Product product);

    void deleteById(Long id);
}
