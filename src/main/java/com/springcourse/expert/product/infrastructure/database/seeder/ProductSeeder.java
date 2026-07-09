package com.springcourse.expert.product.infrastructure.database.seeder;

import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import com.springcourse.expert.product.infrastructure.database.mapper.ProductEntityMapper;
import com.springcourse.expert.product.infrastructure.database.repository.QueryProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

/*
 * ES UNA CLASE QUE SE UTILIZA PARA PODER CORRER COMANDOS E INSERTAR CIERTOS DATOS CUANDO SE CORRA LA
 * APLICACION
 *
 * */

@Component
@RequiredArgsConstructor

/*
 * LA INTERFACE CommandLineRunner PERMITE EJECUTAR COMANDOS
 * DIRECTAMENTE AL INICIALIZAR LA APLICACION (ESTO OCURRE A NIVEL APLICACION NO DEPENDE DEL MODO EN EL QUE CORRA SPRING)
 * */
/*
 * @Profile: Permite definir en que perfiles se van a ejecutar las clases
 * */
@Profile("!test")
public class ProductSeeder implements CommandLineRunner {

    /*
     * FORMA 1:
     *
     * Crear una logica para poder validar cuantos ya estan insertados en la base de datos
     *
     * EJECUTAR COMANDOS PARA PODER VALIDAR CUANTAS TUPLAS YA ESTAN INSERTADAS Y SI YA ESTAN SIMPLEMENTE
     * OMITIR LA OPERACION
     *
     * */
    private final QueryProductRepository productRepository;
    private final ProductEntityMapper mapper;

    /*FORMA 2:
     *
     * PERMITE PODER CARGAR RECURSOS O SEEDERS HECHOS MEDIANTE ARCHIVOS JSON
     * QUE CONTIENE TODA LA INFORMACION OBTENIDA DE LOS JSON Y TRANSFORMAR EL LISTADO DIRECTAMENTE A LA ENTIDAD
     *
     *
     * ES DECIR QUE SE MAPEAN OBJETOS DEL JSON A OBJETOS JAVA
     *
     * */
    private final ResourceLoader resourceLoader;
    /*
     * LIBRERIA DE JACKSON QUE PERMITE TRANSFORMAR JSON A OBJETOS JAVA
     * */
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        long count = productRepository.count();

        if (count == 0) {

            /*PARTE DE FORMA 1:
            List<Product> products = List.of(
                    Product.builder().name("Product 1").description("Description 1").image("image").price(199.10).build(),
                    Product.builder().name("Product 2").description("Description 2").image("image").price(29.00).build()

                     productRepository.saveAll(products.stream().map(mapper::mapToProductEntity).toList());
            );
            */


            /*
             * PERMITE OBTENER EL JSON CON INFORMACION DESDE LOS RECURSOS DE LA APLICACION
             * */
            Resource resource = resourceLoader.getResource("classpath:products.json");
            List<ProductEntity> productEntities = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
            });


            productRepository.saveAll(productEntities);
        }


    }
}
