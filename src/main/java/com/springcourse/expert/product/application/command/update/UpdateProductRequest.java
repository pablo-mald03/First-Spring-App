package com.springcourse.expert.product.application.command.update;

import com.springcourse.expert.common.application.mediator.Request;
import com.springcourse.expert.review.domain.Review;
import lombok.Data;

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
public class UpdateProductRequest implements Request<Void> {

    /*Se Recibe el cuerpo necesario*/
    private Long id;
    private String name;
    private String description;
    private Double price;

    /*(RECUERDO PARA HACER PETICIONES MULTIPART)*/
    //private MultipartFile file;

    private String provider;

    private Review review;

    private Long categoryId;

}
