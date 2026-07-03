package com.springcourse.expert.common.application.mediator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * @Component permite aprovechar la inversion de dependencias automatica que hace spring
 * */
@Component
@Slf4j
public class Mediator {

    Map<? extends Class<?>, RequestHandler<?, ?>> requestHandlerMap;

    /*
     * TODAS LAS CLASES QUE SE TENDRAN EN LA CAPA DE APPLICACION (HANDLER QUE IMPLEMENTEN REQUESTHANDLER) SPRINGBOOT LOS
     * VA A INYECTAR AUTOMATICAMENTE EN EL CONSTRUCTOR
     * */
    public Mediator(List<RequestHandler<?, ?>> requestHandlers) {
        /*
         * CREA UN MAPA CLAVE VALOR DONDE:
         *
         * LLAVE: SERA EL TIPO DE LA CLASE
         *
         * VALOR: SERA LA CLASE QUE LO MANEJA
         *
         * */
        requestHandlerMap = requestHandlers.stream().collect(Collectors.toMap(RequestHandler::getRequestType, Function.identity()));
    }

    /*
     * ESTE METODO PERMITE BUSCAR EN EL REQUESTHANDLERMAP
     * PERMITIENDO OBTENER DE LA ENTRADA SU CLASE Y EN BASE A SU CLASE
     *
     * SE SABRA QUE HANDLER LE CORRESPONDE Y LO RETORNA PARA PODER USARLO
     *
     * SI EL HANDLER ESTA VACIO SIMPLEMENTE SE LANZA LA EXCEPCION DE QUE NO EXISTE
     *
     * R: Es la clase que hace la request
     *
     * */
    public <R, T extends Request<R>> R dispatch(T request) {

        RequestHandler<T, R> handler = (RequestHandler<T, R>) requestHandlerMap.get(request.getClass());

        if (handler == null) {
            log.error("No handler found for request type: {}", request.getClass());
            throw new RuntimeException("No handler found for request type: " + request.getClass());
        }

        return handler.handle(request);
    }

    /*
     * LA NOTACION @Async ejecuta una tarea asincrona, es decir que sera una tarea que se ejecutara posteriormente
     * despues de haber retronado el estado de aceptacion
     *
     *
     * ESTO PERMITE USAR TAREAS ASINCRONAS Y ESTAS TAREAS ASINCRONAS NO RETORNAN NADA.
     *
     * LOS METODOS DEBEN SER DE TIPO void PRIMITIVOS porque no retornan nada
     *
     * Pero tambien puede retornar tipos de datos Future
     * */

    @Async
    public <R, T extends Request<R>> void dispatchAsync(T request) {
        this.dispatch(request);
    }
}
