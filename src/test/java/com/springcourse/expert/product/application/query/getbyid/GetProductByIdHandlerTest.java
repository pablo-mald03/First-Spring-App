package com.springcourse.expert.product.application.query.getbyid;

import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.domain.exception.ProductNotFoundException;
import com.springcourse.expert.product.domain.port.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/*
 * ESTOS TEST SE CONOCEN COMO UNITARIOS
 * PERMITEN TESTEAR LAS LINEAS DE CODIGO Y LOS DATOS ESPERADOS AL CORRER LAS LINEAS DE CODIGO
 *
 * ESTO SE LOGRA GRAICAS A JUnit que permite ir corriendo tests para cada linea de codigo y lo que se requiera probar
 *
 *
 * SIEMPRE ES IMPORTANTE IR PROBANDO LINEAS DE CODIGO QUE SE REQUIERAN ACORDE A LA COMPLEJIDAD DEL CODIGO
 *
 * */

@ExtendWith(MockitoExtension.class)
class GetProductByIdHandlerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductByIdHandler getProductByIdHandler;

    @Test
    void shouldReturnProductWhenFound() {
        // Arrange
        Long productId = 1L;
        Product mockProduct = Product.builder()
                .id(productId)
                .name("Test Product")
                .description("Description")
                .price(100.0)
                .build();
        GetProductByIdRequest request = new GetProductByIdRequest(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        /*
         * SE PUEDEN MOCKEAR METODOS ESTATICOS O METODOS DE CUALQUIER TIPO
         * */
        // Act
        GetProductByIdResponse response = getProductByIdHandler.handle(request);

        // Assert
        assertNotNull(response);
        assertEquals(productId, response.getProduct().getId());
        assertEquals("Test Product", response.getProduct().getName());
        verify(productRepository, times(1)).findById(productId);
    }


    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Arrange
        Long productId = 1L;
        GetProductByIdRequest request = new GetProductByIdRequest(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> getProductByIdHandler.handle(request));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void shouldReturnCorrectRequestType() {
        // Act
        Class<GetProductByIdRequest> requestType = getProductByIdHandler.getRequestType();

        // Assert
        assertEquals(GetProductByIdRequest.class, requestType);
    }
}
