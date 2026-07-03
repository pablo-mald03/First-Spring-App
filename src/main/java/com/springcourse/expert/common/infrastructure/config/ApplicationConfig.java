package com.springcourse.expert.common.infrastructure.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 * Esta clase se almacena en el paquete common.config
 *
 * PORQUE ES UNA CONFIGURACION GENERAL DE LA APLICACION
 *
 * SIEMPRE Y CUANDO ALGO SEA GENERAL SIEMPRE DEBE ESTAR EN COMMON Y EN SU PAQUETE RESPECTIVO
 *
 * */

/*
 * La anotacion @Configuracion en clases permite delegar ciertas configuraciones de spring para poderlas
 * implementarlas en su aplicacion y ayuda a desacoplar el codigo
 *
 * */
@Configuration

/*
 * La anotacion @EnableAsync
 *
 * PERMITE ACTIVAR LAS TAREAS ASINCRONAS EN LA APLICACION
 *
 * */
@EnableAsync

/*
 * La anotacion @EnableScheduling
 *
 * LE INDICA A SPRING QUE EJECUTARA TAREAS PROGRAMADAS
 * */
@EnableScheduling

/*La anotacion @EnableCaching
 *
 * LE INDICA A SPRING QUE CACHEARA INFORMACION REQUERIDA POR EL USUARIO MULTIPLES VECES
 * */
@EnableCaching
public class ApplicationConfig {
}
