package com.springcourse.expert.product.infrastructure.api;

import com.springcourse.expert.common.mediator.Mediator;
import com.springcourse.expert.product.application.command.create.CreateProductRequest;
import com.springcourse.expert.product.application.command.delete.DeleteProductRequest;
import com.springcourse.expert.product.application.command.update.UpdateProductRequest;
import com.springcourse.expert.product.application.query.getall.GetAllProductRequest;
import com.springcourse.expert.product.application.query.getall.GetAllProductResponse;
import com.springcourse.expert.product.application.query.getbyid.GetProductByIdRequest;
import com.springcourse.expert.product.application.query.getbyid.GetProductByIdResponse;
import com.springcourse.expert.product.infrastructure.api.dto.CreateProductDto;
import com.springcourse.expert.product.infrastructure.api.dto.ProductDto;
import com.springcourse.expert.product.infrastructure.api.dto.UpdateProductDto;
import com.springcourse.expert.product.infrastructure.api.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
/*
 * ES EL NOMBRE QUE SE LE QUIERE DAR A LA DIRECCION DEL CONTROLADOR PARA PODERLA IDENTIFICAR
 * */
@Tag(name = "Product", description = "Product API operations")
/*Anotacion de lombok para poder logguear*/
@Slf4j
public class ProductController implements ProductRestController {


    private final Mediator mediator;
    private final ProductMapper productMapper;

    /*
     * ResponseEntity tiene el scope mientras vive la peticion hasta que retorna
     * posteriormente se elimina en el garbage collector
     *
     * required= false
     *
     * INDICA QUE PUEDEN O NO VENIR LOS PARAMETROS ESPECIFICADOS
     *
     * */
    @Operation(summary = "Get all products", description = "Get all products")
    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false) String pageSize) {

        log.info("Getting all products");
        GetAllProductResponse response = mediator.dispatch(new GetAllProductRequest());

        List<ProductDto> productDtos = response.getProduct().stream().map(productMapper::mapToProductDto).toList();

        log.info("Found {} products", productDtos.size());
        return ResponseEntity.ok(productDtos);
    }

    @Operation(summary = "Get product by id", description = "Get product by id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {

        log.info("Getting product with id: {}", id);

        /*SI SE HAGE LA DECLARACION MANUAL SI SE DEBE ESPECIFICAR LA NOTACION
         * @AllArgsConstructor PORQUE SINO CAUSARA PROBLEMAS
         * */
        GetProductByIdResponse response = mediator.dispatch(new GetProductByIdRequest(id));

        /*
         * Los responses ya se dan automaticamente por el tipo de arquitectura hexagonal
         * */
        ProductDto product = productMapper.mapToProductDto(response.getProduct());

        log.info("Found product with id: {}", product.getId());
        return ResponseEntity.ok(product);

    }

    /*
     * IMPORTANTE:
     *
     *      @RequestBody: ESPERA UN CONTENTTYPE DE TIPO JSON O DE TEXTO
     *
     * PERO A LA HORA DE MANEJAR UN MULTIPART SE ESTA MANEJANDO OTRO TIPO DE CONTENTTYPE POR LO TANTO SE USA:
     *      @ModelAttribute: ESPERA DATOS DE UN FORMULARIO HTML DE TIPO DATA
     *
     * */
    @Operation(summary = "Save product", description = "Save product")
    @PostMapping("")
    public ResponseEntity<Void> saveProduct(@ModelAttribute @Valid CreateProductDto product) {

        log.info("Saving product with id {}", product.getId());

        /*PERMITE MAPEAR EL DTO QUE VIENE EN EL REQUEST HACIA EL HANDLER*/
        CreateProductRequest request = productMapper.mapToCreateProductRequest(product);

        mediator.dispatch(request);

        log.info("Product with id {} was saved", product.getId());

        return ResponseEntity.created(URI.create("/api/v1/products/".concat(product.getId().toString()))).build();
    }

    /*
     * IMPORTANTE:
     *
     *      @RequestBody: ESPERA UN CONTENTTYPE DE TIPO JSON O DE TEXTO
     *
     * PERO A LA HORA DE MANEJAR UN MULTIPART SE ESTA MANEJANDO OTRO TIPO DE CONTENTTYPE POR LO TANTO SE USA:
     *      @ModelAttribute: ESPERA DATOS DE UN FORMULARIO HTML DE TIPO DATA
     *
     * */
    @Operation(summary = "Update product", description = "Update product")
    @PutMapping
    public ResponseEntity<Void> updateProduct(@ModelAttribute @Valid UpdateProductDto product) {

        log.info("Updating product with id {}", product.getId());

        /*PERMITE MAPEAR EL DTO QUE VIENE EN EL REQUEST HACIA EL HANDLER*/
        UpdateProductRequest request = productMapper.mapToUpdateProductRequest(product);
        mediator.dispatch(request);

        log.info("Product with id {} was updated", product.getId());
        return ResponseEntity.noContent().build();
    }

    /*
     * UNA TAREA ASINCRONICA ES LA QUE SE EJECUTA PARA TAREAS MUY PESADAS
     * YA QUE NO PUEDE TARDAR TANTO LA APLICACION EN RETORNAR UNA RESPUESTA
     *
     * SE USAN DIFERENTES HILOS QUE EJECUTAN LA PETICION
     *
     *
     * ES BUENA PRACTICA EN LUGAR DE RETORNAR UN ESTADO noContent();
     *
     * SE RETORNA UN accepted(); CUANDO ESTA UTILIZANDO UNA TAREA ASINCRONA
     *
     * CASO CONTRARIO DE NO SER TAREA ASINCRONA se retorna NO CONTENT
     * */
    @Operation(summary = "Delete product", description = "Delete product")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        log.info("Deleting product with id {}", id);
        mediator.dispatchAsync(new DeleteProductRequest(id));
        log.info("Product with id {} was deleted", id);
        return ResponseEntity.accepted().build();
    }
}
