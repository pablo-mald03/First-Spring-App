package com.springcourse.expert.common.application.mediator;

/*
 * T: Es el tipo de clase que hara la peticion ES LA RESPECTIVA ENTRADA
 *
 * El primer parametro sera el que implemente la interface Request, es decir QUE EXTIENDE DE LA CLASE REQUEST
 * PERO TIENE UN VALOR R.
 *
 *  Donde: DEBE TENER EL TIPO:
 *          R: ESTE SERA LA RESPUESTA Y DEBE TENER EL R PARA EL TIPO DE RESPUESTA
 *
 * R: SERA LA RESPUESTA QUE SE RETORNE O SE VA A DEVOLVER MEDIANTE EL CONTROLADOR
 * */
public interface RequestHandler<T extends Request<R>, R> {

    /*
     * Este metodo retorna la respuesta de la peticion
     *
     * Depende directamente de la clase que implementa la interface
     *
     * */
    R handle(T request);

    /*
     * Metodo que permite retornar el tipo de peticion que se hizo
     *
     *
     * */
    Class<T> getRequestType();
}
