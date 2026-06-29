package com.springcourse.expert.product.infrastructure.api;

import com.springcourse.expert.common.mediator.Mediator;
import com.springcourse.expert.product.application.query.getall.GetAllProductRequest;
import com.springcourse.expert.product.application.query.getall.GetAllProductResponse;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.infrastructure.api.dto.ProductDto;
import com.springcourse.expert.product.infrastructure.api.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
 * ESTA ANOTACION @ExtendWith(MockitoExtension.class)
 *
 * VA A PERMITIR QUE TODOS LOS MOCKS ESTEN ABIERTOS Y PREPARADOS PARA PODER PROBARLOS
 *
 *
 * ESTO VIENE DE org.junit.jupiter
 *
 * LO QUE YA VIENE INTEGRADO AUTOMATICAMENTE EN SPRING PARA PODER TESTEAR LA API
 *
 * */
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    /*
     * Se especifican las clases que se van a probar
     *
     * ES DECIR. QUE SE ESPECIFICAN TODAS LAS INSTANCIAS QUE REQUIERA PARA PODER TESTEAR
     *
     * */

    /*
     * UN MOCK INDICA QUE SE CREARA UNA CLASE DE TIPO MEDIATOR
     * PERO INTERNAMENTE SE CREARA UN PROXY QUE INTERCEPTARA LAS PETICIONES DEL MEDIATOR
     *
     * VA A METER LA LOGICA QUE SE LE DEFINA
     * */
    @Mock
    private Mediator mediator;

    @Mock
    private ProductMapper productMapper;

    /*Permite que se le puedan inyectar datos de tests*/
    @InjectMocks
    private ProductController productController;

    /*
     * LA NOTACION @Test
     *
     * PERMITE INDICAR QUE SERA UN METODO QUE SE VA A PROBAR
     * */
    @Test
    public void getAllProducts() {

        /*SE DAN VALORES PREDETERMINADOS PARA PODER RETORNAR
         * Y PODER UTILIZAR*/
        GetAllProductResponse getAllProductResponse = new GetAllProductResponse(List.of(

                Product.builder().id(1L).build(),
                Product.builder().id(2L).build()
        ));

        /*Este metodo indica la accion que se hara cuando se retorne
         * cierta informacion del metodo especificado
         * */
        when(mediator.dispatch(any(GetAllProductRequest.class))).thenReturn(getAllProductResponse);

        /*
         * DEPENDE DEL PROGRAMADOR QUE DESEA TESTEAR Y ESPECIFICARA LOS DATOS DE RETORNO QUE SE HARAN
         * Y CON QUE PARAMETROS SE RETORNARAN
         * */
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);


        /*
         * thenReturn(productDto): Simula el dato que se va a retornar como respuesta
         *
         * SE EMULA EL CODIGO QUE SE VA A IR HACIENDO ES DECIR LINEA A LINEA CUANDO SE REALICE CIERTA ACCION Y SE ESPERE ALGO COMO VALOR DE RETORNO
         * */
        when(productMapper.mapToProductDto(any(Product.class))).thenReturn(productDto);

        ResponseEntity<List<ProductDto>> response = productController.getAllProducts("5");

        /*
         * LAS ASSERTIONS DEFINEN QUE ES LO QUE SE ESPERABA DIRECTAMENTE DE LAS LINEAS DE CODIGO
         * QUE SE LLAMARON O DEFINIR CUANTOS DATOS O QUE COSAS SE ESPERABAN
         *
         * */
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        /*
         * SE PUEDEN GENERAR TESTS AUTOMATICOS. DESDE EL PROPIO IDE
         * */
        List<ProductDto> products = response.getBody();
        assertEquals(2, products.size());
    }
}