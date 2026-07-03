package com.springcourse.expert.product.application.command.create;

import com.springcourse.expert.common.application.mediator.Request;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/*
 * SIEMPRE SE RECOMIENDA SEGUIR EL ESTANDAR
 *
 * NameObjectRequest
 *
 *
 * El tipo vacio es cuando no se va a devolver nada solo se
 * ejecutara una accion Y ESTA CLASE SERA LA QUE RECIBIRA PROPIEDADES PARA CREAR UN REQUEST
 *
 * */
@Data

/*
 *
 * ReturnType: Dato a retornar o tipo a retornar tras generar su funcion
 *
 *
 * Request<ReturnType>
 *
 * */
public class CreateProductRequest implements Request<CreateProductResponse> {

    /*no suele ser tipico pedir ID PORQUE SE HACE DESDE LA BASE DE DATOS.
     * PERO DE MOMENTO ES PROVISIONAL*/
    // private Long id;
    private String name;
    private String description;
    private Double price;
    private MultipartFile file;
}
