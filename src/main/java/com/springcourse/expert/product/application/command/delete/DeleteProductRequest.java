package com.springcourse.expert.product.application.command.delete;

import com.springcourse.expert.common.application.mediator.Request;
import lombok.AllArgsConstructor;
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
/*
 *SIEMPRE ES NECESARIO PONER @AllArgsConstructor SI EL OBJETO REQUIERE CAMPOS O SERA CONVERTIDO A OTRO TIPO*/
@AllArgsConstructor
public class DeleteProductRequest implements Request<Void> {

    /*SOLO SE RECIBE EL EL CUERPO DE LA PETICION*/
    private Long id;
}
