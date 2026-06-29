package com.springcourse.expert.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;


/*
 * ESTA ANOTACION DEFINE TODO LO QUE SE VA A MOSTRAR EN LA CABEZA DE LA PAGINA
 * PERMITE AGREGAR DESCRIPCIONES, INFORMACION Y SERVIDORES EN LOS QUE SE DESPLEGA LA APLICACION
 * */
@OpenAPIDefinition(
        info = @Info(
                title = "My First API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Pablo", email = "pablito@gmail.com", url = "https://pablito.org"
                ),
                description = "My first API realese with Spring Boot",
                /*PERMITE REGISTRAR UNA LICENCIA O MOSTRAR UNA LICENCIA*/
                license = @License(
                        name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        /*PERMITE DEFINIR LOS SERVIDORES EN LOS QUE SE ENCUENTRA*/
        servers = @Server(
                url = "http://localhost:8080",
                description = "Production"
        )
)
@Configuration
public class OpenAPIConfig {
}
