package com.springcourse.expert.product.application.command.delete;

import com.springcourse.expert.common.application.mediator.RequestHandler;
import com.springcourse.expert.product.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
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
public class DeleteProductHandler implements RequestHandler<DeleteProductRequest, Void> {

    private final ProductRepository productRepository;

    /*TAMBIEN SE PUEDE DEJAR EL CONSTRUCTOR DIRECTAMENTE PARA HACER LA INVERSION DE DEPENDENCIAS*/
   /* public ProductCreateHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }*/


    /*
     * handle: es la ejecucion de la accion a realizar
     * */
    @Override
    public Void handle(DeleteProductRequest request) {

        System.out.println("Deleting product with ID: " + request.getId() + "...");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        productRepository.deleteById(request.getId());

        System.out.println("Product with ID: " + request.getId() + " was deleted...");
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
    public Class<DeleteProductRequest> getRequestType() {
        return DeleteProductRequest.class;
    }
}
