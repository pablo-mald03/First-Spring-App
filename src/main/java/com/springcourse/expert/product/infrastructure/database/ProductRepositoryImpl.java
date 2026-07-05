package com.springcourse.expert.product.infrastructure.database;

import com.springcourse.expert.common.domain.PaginationQuery;
import com.springcourse.expert.common.domain.PaginationResult;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.domain.entity.ProductFilter;
import com.springcourse.expert.product.domain.port.ProductRepository;
import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import com.springcourse.expert.product.infrastructure.database.mapper.ProductEntityMapper;
import com.springcourse.expert.product.infrastructure.database.repository.QueryProductRepository;
import com.springcourse.expert.product.infrastructure.database.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

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

    /*LISTA LOCAL DE INFORMACION (RECUERDO)*/
    // private final List<ProductEntity> productList = new ArrayList<>();

    private final QueryProductRepository productRepository;

    private final ProductEntityMapper productEntityMapper;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productEntityMapper.mapToProductEntity(product);
        ProductEntity productResponse = productRepository.save(productEntity);
        return productEntityMapper.mapToProduct(productResponse);
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
        /*(RECUERDO)*/
        // return productList.stream().filter(product -> product.getId().equals(id)).findFirst().map(productEntityMapper::mapToProduct);

        //(RECUERDO SIN RELACION)
        //return productRepository.findById(id).map(productEntityMapper::mapToProduct);
        return productRepository.findById(id).map(productEntityMapper::mapToProduct);
    }

    /*(RECUERDO SIN PAGINACION)*/
//    @Override
//    public List<Product> findAll() {
//        /*(RECUERDO)*/
//        //return productList.stream().map(productEntityMapper::mapToProduct).toList();
//        return productRepository.findAll().stream().map(productEntityMapper::mapToProduct).toList();
//    }

    /*
     * FindAll Con paginacion
     * */
    @Override
    public PaginationResult<Product> findAll(PaginationQuery paginationQuery, ProductFilter productFilter) {

        /*SE PUEDEN ESPECIFICAR LA DIRECCION DE LOS OBJETOS BUSCADOS Y ALGUN FILTRO O PARAMETRO DE REFERENCIAR*/
        PageRequest pageRequest = PageRequest.of(
                paginationQuery.getPage(),
                paginationQuery.getSize(),
                Sort.by(Sort.Direction.fromString(paginationQuery.getDirection()), paginationQuery.getSortBy())
        );

        /*
         * CON LAS SPECIFICACIONES DE JPA SE PUEDEN ESPECIFICAR FILTROS DINAMICOS
         * DE ESTA FORMA SE PUEDEN CREAR PAGINACIONES MAS FACILES
         * */
        Specification<ProductEntity> specification = Specification.allOf(
                ProductSpecification.byName(productFilter.getName())
                        .and(ProductSpecification.byDescription(productFilter.getDescription())
                                .and(ProductSpecification.byPrice(productFilter.getPriceMin(), productFilter.getPriceMax())))

        );

        Page<ProductEntity> page = productRepository.findAll(specification, pageRequest);
        return new PaginationResult<>(
                page.getContent().stream().map(productEntityMapper::mapToProduct).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()

        );
    }

    /*
     * EJEMPLO DE EAGGER VS LAZY fetchType
     *
     * */
    @Override
    public Product update(Product product) {
        ProductEntity productEntity = productEntityMapper.mapToProductEntity(product);
        /*(RECUERDO)*/
       /* productList.removeIf(p -> p.getId().equals(productEntity.getId()));
        productList.add(productEntity);*/
        ProductEntity productResponse = productRepository.save(productEntity);

        /*
         * Si se tiene configurado el fetchType EAGGER ya se carga directamente la relacion
         * cuando se esta llamando el Entity
         *
         * Si se tiene configurado el fetchType LAZY se carga SOLO CUANDO SE LLAMA AL METODO
         * cuando se esta llamando el Entity
         * */
        //ProductDetailEntity productDetailEntity = productEntity.getProductDetail();

        return productEntityMapper.mapToProduct(productResponse);
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
        /*(RECUERDO)*/
        //productList.removeIf(product -> product.getId().equals(id));
        productRepository.deleteById(id);
    }
}
