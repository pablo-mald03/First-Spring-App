package com.springcourse.expert.product.application.command.update;

import com.springcourse.expert.category.domain.Category;
import com.springcourse.expert.category.infrastructure.CategoryEntityMapper;
import com.springcourse.expert.category.infrastructure.QueryCategoryRepository;
import com.springcourse.expert.common.application.mediator.RequestHandler;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.domain.exception.ProductNotFoundException;
import com.springcourse.expert.product.domain.port.ProductRepository;
import com.springcourse.expert.productDetail.domain.ProductDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
 * LAS ACCIONES QUE NO RETORNAN NADA ES DECIR SON DE TIPO "Void"
 *
 * VAN DENTRO DEL PAQUETE DE command
 *
 *
 * SIEMPRE SE RECOMIENDA SEGUIR EL ESTANDAR
 *
 * NameObjectHandler
 * SE PUEDE ANOTAR COMO UN SERVICE O COMO COMPONENT
 *
 * YA QUE LO QUE BRINDA SON ACCIONES
 *
 * */
@Service

/*
 * TAMBIEN SE PUEDE USAR LOMBOK PARA AHORRARSE EL CONSTRUCTOR PERO ES A DECISION DEL PROGRAMADOR
 *
 *
 * */
/*
 *
 * ESTA ANOTACION SE USA PARA PODER REALIZAR INVERSION DE DEPENDENCIAS YA QUE REQUIERE UN CONSTRUCTOR CON TODAS LAS INSTANCIAS O CONSTANTES
 * DECLARADAS EN LA CLASE
 * */
@RequiredArgsConstructor

/*
 *
 * ReturnType: Dato a retornar o tipo a retornar
 *
 * ClassImpl: Clase que extiende de Request
 *
 * RequestHandler<ClassImpl, ReturnType>
 *
 * */
@Slf4j
/*
 * LA ANOTACION TRANSACTIONAL permite indicar que las operaciones se haran dentro de una excepcion
 *
 * ESTA TIENE LAS SIGUIENTES PROPIEDADES:
 *
 * rollbackOn = EXCEPCION CON LA QUE SE QUIERE QUE SE HAGA EXCEPCION
 *
 * dontRollbackOn = EXCEPCION CON LA QUE NO SE QUIERE QUE SE HAGA ROLLBACK
 *
 *
 * SE HACE A NIVEL DE LA CLASE EN LA QUE SE REQUIERE CONTROLAR TODA LA TRANSACCION. NO DIRECTAMENTE
 * EN CADA METODO EN EL QUE SE REQUIERA. MEJOR BUSCAR UNA CLASE DONDE OCURREN LOS PROCESOS
 *
 * DEPENDIENDO DEL CASO DE USO SE PUEDEN IMPLEMENTAR MAS
 *
 * */
@Transactional(rollbackOn = Exception.class)
public class UpdateProductHandler implements RequestHandler<UpdateProductRequest, Void> {

    private final ProductRepository productRepository;

    //(RECUERDO DEL MANEJO DEL SERVICE DE FILES)
    // private final FileUtilService fileUtilService;
    private final QueryCategoryRepository queryCategoryRepository;

    private final CategoryEntityMapper categoryEntityMapper;


    /*TAMBIEN SE PUEDE DEJAR EL CONSTRUCTOR DIRECTAMENTE PARA HACER LA INVERSION DE DEPENDENCIAS*/
   /* public ProductCreateHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }*/


    /*
     * handle: es la ejecucion de la accion a realizar
     * */
    @Override
    public Void handle(UpdateProductRequest request) {

        log.info("Updating product with id {}", request.getId());

        /*
         * (RECUERDO DEL MANEJO DE MULTIPART)
         * */
//        String uniqueFileName = fileUtilService.saveProductImage(request.getFile());
//
//        Product product = Product.builder()
//                .id(request.getId())
//                .name(request.getName())
//                .description(request.getDescription())
//                .price(request.getPrice())
//                .image(uniqueFileName)
//                .build();

        Product product = productRepository.findById(request.getId()).orElseThrow(() -> new ProductNotFoundException(request.getId()));

        ProductDetail productDetail = product.getProductDetail();

        productDetail.setProvider(request.getProvider());

        product.getReviews().add(request.getReview());

        Category category = queryCategoryRepository.findById(request.getCategoryId())
                .map(categoryEntityMapper::mapToCategory)
                .orElseThrow(() -> new RuntimeException("Category not found"));


        product.getCategories().add(category);

        productRepository.update(product);

        if (product.getId() == 30) throw new RuntimeException("Error updating product");

        log.info("Product with id {} was updated", request.getId());

        return null;
    }

    /*
     * Permite saber cual es la clase de entrada
     *
     * ES MEJOR HACERLO DIRACTAMENTE CON UN METODO YA QUE JAVA EN TIEMPO DE EJECUCION
     * HA CARGADO LO NECESARIO
     *
     * AL CONTRARIO DE USAR REFLEXION (QUE CONSUME MUCHA MEMORIA)
     *
     * */
    @Override
    public Class<UpdateProductRequest> getRequestType() {
        return UpdateProductRequest.class;
    }
}
