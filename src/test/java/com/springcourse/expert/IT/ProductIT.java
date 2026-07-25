package com.springcourse.expert.IT;

import com.springcourse.expert.product.domain.port.ProductRepository;
import com.springcourse.expert.product.infrastructure.api.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * LA ANOTACION @SpringBootTest permite generar pruebas de integracion desde una red ficticia o desde algun
 * puerto ficticio para poder generar las pruebas
 *
 * YA CONFIGURA AUTOMATICO EL PUERTO
 * */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@Slf4j
/*
 * La anotacion PERMITE TESTEAR MULTIPART
 *
 * */
@AutoConfigureMockMvc
class ProductIT {

    @Autowired
    @Qualifier("restTemplate")
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    /*CLASE QUE PERMITE TESTEAR LOS ENDPOINTS CON MULTIPART*/
    @Autowired
    private MockMvc mockMvc;

    /*
     * Metodo que permite generar instancias o valores. Es decir generar valores para poder testear los metodos
     * ya que se ejecuta el metodo antes de iniciar el test
     * */
//    @BeforeEach
//    void setUp() {
//        log.info("Setting up integration tests");
//        productRepository.save(Product.builder().id(1L).name("Product 1").description("Description 1").price(100.0).build());
//
//    }
//
//    /*
//     * Metodo que permite
//     *
//     * */
//    @AfterEach
//    void tearDown() {
//
//        log.info("Tearing down integration tests");
//        productRepository.deleteById(1L);
//    }

    /*
     * SE ESPECIFICAN LOS PARAMETROS A SEGUIR ANTES DE EJECUTAR EL TEST DE INTEGRACION
     *
     * Y LUEGO COMO VA A LIMPIAR LOS DATOS INSERTADOS EN LA TABLA
     * */
    @Sql(value = "/it/product/findById/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/it/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void getProductByIDExists() {

        ResponseEntity<ProductDto> response =
                restTemplate.getForEntity("/api/v1/products/1", ProductDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals("Product 1", response.getBody().getName());
        assertEquals("Description 1", response.getBody().getDescription());
        assertEquals(199.0, response.getBody().getPrice());

    }

    @Sql(value = "/it/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void saveProduct() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "image.jpeg", "image/jpeg", "image".getBytes());

        mockMvc.perform(multipart(HttpMethod.POST, "/api/v1/products")
                .file(file)
                .param("id", "2")
                .param("name", "Name 2")
                .param("description", "Description 2")
                .param("price", "200.0")
                .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isCreated());
    }
}
