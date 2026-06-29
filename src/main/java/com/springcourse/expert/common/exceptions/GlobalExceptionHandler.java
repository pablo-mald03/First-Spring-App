package com.springcourse.expert.common.exceptions;

import com.springcourse.expert.product.domain.exception.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*Se especifica el tipo de error HTTP se tiene*/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    /*Se especifica que excepcion o excepciones va a capturar*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    /*Indica que retornara un cuerpo el error*/
    @ResponseBody
    public ErrorMessage badRequest(HttpServletRequest request, MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();
        /*Crea un mapa con los campos de la peticion que han fallado en la peticion
         *
         * getField(): Campo que ha fallado en la validacion
         * getDefaultMessage(): Retorna el mensaje que ha soltado la excepcion
         * */
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
        {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return new ErrorMessage(exception.getMessage(), exception.getClass().getSimpleName(), request.getRequestURI(), errors);
    }


    /*Se permite capturar tambien excepciones creadas para cada caso de uso*/
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    public ErrorMessage notFound(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception.getMessage(), exception.getClass().getSimpleName(), request.getRequestURI());
    }
}
