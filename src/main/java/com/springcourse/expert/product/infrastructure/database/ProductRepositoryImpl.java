package com.springcourse.expert.product.infrastructure.database;

import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.domain.port.ProductRepository;
import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import com.springcourse.expert.product.infrastructure.database.mapper.ProductEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j

/*
 * SI HAY VARIAS IMPLEMENTACIONES DE LA INTERFACE QUE VIENE DEL PUERTO
 *
 * SIMPLEMENTE SE PUEDE GENERAR LA ANOTACION
 * @Primary
 *
 * IDENTIFICA COMO INSTANCIA PRIMARIA A INYECTAR
 *
 *
 * */
public class ProductRepositoryImpl implements ProductRepository {

    private final List<ProductEntity> productList = new ArrayList<>();

    private final ProductEntityMapper productEntityMapper;

    @Override
    public void save(Product product) {
        ProductEntity productEntity = productEntityMapper.mapToProductEntity(product);
        productList.add(productEntity);
    }

    /*(FORMA MAS FACIL DE HACERLO PENDIENTE INTEGRACION REAL)
     *
     * La anotacion @Cacheable(value = "value", key = "#key")
     *
     * LE PERMITE A SPRING INDICAR QUE ESA INFORMACION SE VA A CACHEAR PARA QUE SEA MAS RAPIDO EL ACCESO
     *
     * LOS VALORES ESPECIFICADOS SON:
     *
     *  value = "valor del que hace referencia"
     *  key = "la llave con la que se va a localizar (FORMA PARTE DE UN PARAMETRO DE IDENTIFICACION UNICA)"
     *
     * */
    @Cacheable(value = "products", key = "#id")
    @Override
    public Optional<Product> findById(Long id) {
        log.info("Getting product with id {}", id);
        return productList.stream().filter(product -> product.getId().equals(id)).findFirst().map(productEntityMapper::mapToProduct);
    }

    @Override
    public List<Product> findAll() {
        return productList.stream().map(productEntityMapper::mapToProduct).toList();
    }

    @Override
    public void update(Product product) {
        ProductEntity productEntity = productEntityMapper.mapToProductEntity(product);
        productList.removeIf(p -> p.getId().equals(productEntity.getId()));
        productList.add(productEntity);
    }

    /*(FORMA MAS FACIL DE HACERLO PENDIENTE INTEGRACION REAL)
     * La notacion @CacheEvict(value = "products", key = "#id")
     *
     * LE PERMITE A SPRING INDICAR QUE SI HAY INFORMACION CACHEADA CON UN IDENTIFICADOR.
     * LA CANCELE Y SE QUITE DE LA CACHE
     *
     *  value = "valor del que hace referencia"
     *  key = "la llave con la que se va a localizar (FORMA PARTE DE UN PARAMETRO DE IDENTIFICACION UNICA)"
     *
     * */
    @CacheEvict(value = "products", key = "#id")
    @Override
    public void deleteById(Long id) {
        productList.removeIf(product -> product.getId().equals(id));
    }
}
