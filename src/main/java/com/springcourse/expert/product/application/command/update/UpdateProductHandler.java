package com.springcourse.expert.product.application.command.update;

import com.springcourse.expert.common.mediator.RequestHandler;
import com.springcourse.expert.common.util.FileUtilService;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.domain.port.ProductRepository;
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
public class UpdateProductHandler implements RequestHandler<UpdateProductRequest, Void> {

    private final ProductRepository productRepository;
    private final FileUtilService fileUtilService;

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

        String uniqueFileName = fileUtilService.saveProductImage(request.getFile());

        Product product = Product.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .image(uniqueFileName)
                .build();
        productRepository.update(product);
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
