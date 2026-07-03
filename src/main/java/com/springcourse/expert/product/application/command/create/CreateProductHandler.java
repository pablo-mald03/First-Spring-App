package com.springcourse.expert.product.application.command.create;

import com.springcourse.expert.common.application.mediator.RequestHandler;
import com.springcourse.expert.common.infrastructure.util.FileUtilService;
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
public class CreateProductHandler implements RequestHandler<CreateProductRequest, CreateProductResponse> {

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
    public CreateProductResponse handle(CreateProductRequest request) {

        log.info("Creating product ");

        String uniqueFileName = fileUtilService.saveProductImage(request.getFile());

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .image(uniqueFileName)
                .build();
        Product saved = productRepository.save(product);
        log.info("Product with id {} was created", saved.getId());


        /*LOS VOID RETORNAN NULL PORQUE NO RETORNAN NADA*/
        return new CreateProductResponse(saved);
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
    public Class<CreateProductRequest> getRequestType() {
        return CreateProductRequest.class;
    }
}
