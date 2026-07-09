package com.springcourse.expert.product.infrastructure.database;

import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import com.springcourse.expert.product.infrastructure.database.repository.QueryProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * TEST CREADO PARA PODER EVALUAR LA DATA QUE RETORNA EL REPOSITORIO DE PRODUCTO
 *
 * PARA PODER TESTEAR UNA FUNCIONALIDAD DIRECTA DE JPA ESTE NO NECESITA EXTENDER DE @ExtendWith(MockitoExtension.class)
 *
 * SINO QUE SE REQUIERE LA ANOTACION ESPECIAL @DataJpaTest
 *
 * QUE CARGA SOLAMENTE LOS COMPONENTES DE JPA
 *
 * */
@DataJpaTest
class ProductRepositoryImplJpaTest {

    @Autowired
    private QueryProductRepository repository;


    /*
     * TEST QUE PERMITE EVALUAR CUANDO EL METODO SE EJECUTA LA CONSULTA A JPA
     * */
    @Test
    void shouldNotReturnProductWhenNotFound() {

        Optional<ProductEntity> optionalProduct = repository.findById(1L);
        assertTrue(optionalProduct.isEmpty());
    }

    /*
     * TEST QUE PERMITE VERIFICAR CUANDO EL METODO RETORNA ALGO
     *
     * POR LO TANTO SE LE PERMITE VERIFICAR SI AL RETORNAR ALGO ESTE COINCIDE CON EL MOCK ESPECIFICADO
     * */
    @Test
    void shouldReturnProductWhenFound() {

        /*
         * SI SE QUIERE VERIFICAR QUE PRODUCTO EXISTE EN LA BASE DE DATOS
         *
         * AL USAR UNA BASE DE DATOS H2 EN RUNTIME SE REQUIERE CREAR UN NUEVO REGISTRO PARA PODER
         * VERIFICAR QUE LA BASE DE DATOS RUNTIME EXISTA REALMENTE EL DATO
         * */
//        Optional<ProductEntity> optionalProduct = repository.findById(1L);
//        assertTrue(optionalProduct.isPresent());

        /*
         * POR LO TANTO ANTES DE VERIFICAR SE DEBE INSERTAR
         * */

        ProductEntity productEntity = new ProductEntity();
        ProductEntity save = repository.save(productEntity);

        Optional<ProductEntity> optionalProduct = repository.findById(save.getId());
        assertTrue(optionalProduct.isPresent());

    }
}