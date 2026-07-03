package com.springcourse.expert.product.application.scheduling;

import com.springcourse.expert.product.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/*
 * LAS CLASES QUE GENEREN O VAN A GENERAR TAREAS PROGRAMADAS SE DEBEN RESPETAR CON EL FORMATO:
 *
 * UseCaseObjectSchedule
 *
 * EN ESTE CASO SIEMPRE VAN EN EL PAQUETE application YA QUE FORMAN PARTE DEL DOMINIO O CAPA DE LA APLICACION
 *
 *SE EMPAQUETAN EN EL PAQUETE scheduling YA QUE REPRESENTA TAREAS PROGRAMADAS EN CIERTO TIEMPO Y ES UN @Service
 *
 * */
@Service
/*
 * Necesita un constructor obligatorio PORQUE GENERA INVERSIONES DE DEPENDENCIAS
 *
 * */
@RequiredArgsConstructor
/*
 * ES UNA NOTACION LOMBOK QUE PERMITE GENERAR LOGS EN LA CLASE O LO QUE OCURRE (Logger)
 * */
@Slf4j
public class FixProductsPriceSchedule {

    private final ProductRepository productRepository;

    /*
     * LA NOTACION @Scheduled
     *
     *  PERMITE INDICAR QUE SE EJECUTARA UNA ACCION DURANTE EL INTERVALO DE TIEMPO O EL TIEMPO JUSTO QUE SE LE INDIQUE
     *
     *  EXISTEN 2 FORMAS:
     *
     *      INDICAR LA HORA EN LA QUE SE EJECUTARA LA ACCION
     *      (cron = "sec min hour day month weekday")
     *      @Scheduled(cron = "0 0 10 * * *")
     *
     *      SI NO SE REQUIEREN USAR CIERTOS PARAMETROS SOLO SE CENSURAN COLOCANDO "*"
     *
     *      INDICAR EL TIEMPO O A CADA CUANTO EJECUTARA LA ACCION (MILISEGUNDOS)
     *      (fixedRate = milisecs)
     *      @Scheduled(fixedRate = 6000)
     *
     *
     */
    @Scheduled(fixedRate = 10000)
    public void fixProductsPrice() {
        /*
         * Para poder dar un mensaje en consola o notificar un LOG se debe usar el comando: (ES PARTE DE LOMBOK)
         * Existen varios tipos de logs Y ESTOS SON MUY UTILIZADOS PARA PODER NOTIFICAR CIERTAS ACCIONES:
         *
         * USADOS PARA PROGRAMAS DE LOGS:
         *
         * .info
         * .error
         * .warning
         * */
        log.info("Fixing products price");

//        productRepository.findAll().forEach(product -> {
//            product.setPrice(product.getPrice() * 1.1);
//            productRepository.update(product);
//        });

        log.info("Finished fixing products price");
    }
}
