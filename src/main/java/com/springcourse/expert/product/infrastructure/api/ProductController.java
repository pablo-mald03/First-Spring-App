package com.springcourse.expert.product.infrastructure.api;

import com.springcourse.expert.common.application.mediator.Mediator;
import com.springcourse.expert.common.domain.PaginationQuery;
import com.springcourse.expert.common.domain.PaginationResult;
import com.springcourse.expert.product.application.command.create.CreateProductRequest;
import com.springcourse.expert.product.application.command.create.CreateProductResponse;
import com.springcourse.expert.product.application.command.delete.DeleteProductRequest;
import com.springcourse.expert.product.application.command.update.UpdateProductRequest;
import com.springcourse.expert.product.application.query.getall.GetAllProductRequest;
import com.springcourse.expert.product.application.query.getall.GetAllProductResponse;
import com.springcourse.expert.product.application.query.getbyid.GetProductByIdRequest;
import com.springcourse.expert.product.application.query.getbyid.GetProductByIdResponse;
import com.springcourse.expert.product.domain.entity.Product;
import com.springcourse.expert.product.domain.entity.ProductFilter;
import com.springcourse.expert.product.infrastructure.api.dto.CreateProductDto;
import com.springcourse.expert.product.infrastructure.api.dto.ProductDto;
import com.springcourse.expert.product.infrastructure.api.dto.UpdateProductDto;
import com.springcourse.expert.product.infrastructure.api.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
/*
 * ES EL NOMBRE QUE SE LE QUIERE DAR A LA DIRECCION DEL CONTROLADOR PARA PODERLA IDENTIFICAR
 * */
@Tag(name = "Product", description = "Product API operations")
/*Anotacion de lombok para poder logguear*/
@Slf4j

/*
 * DECORADOR QUE PERMITE ESPECIFICAR EN LA PAGINA DE LA UI QUE APAREZCA CON AUTENTICACION REQUERIDA
 * */
@SecurityRequirement(name = "Bearer Authentication")
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
     * DE ESTA FORMA SE PUEDE PODER OFRECER PAGINACION DESDE LA API CON LOS SIGUIENTES ATRIBUTOS:
     *
     * PARA EVITAR BUSCAR LA INFORMACION DE UN SOLO
     *
     * (RECUERDO)
     * */
//    @Operation(summary = "Get all products", description = "Get all products")
//    @GetMapping("")
//    public ResponseEntity<PaginationResult<ProductDto>> getAllProducts(
//            @RequestParam(defaultValue = "0") int pageNumber,
//            @RequestParam(defaultValue = "5") int pageSize,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "asc") String direction,
//            @RequestParam(required = false) String name
//    ) {
//
//        log.info("Getting all products");
//
//        PaginationQuery paginationQuery = new PaginationQuery(pageNumber, pageSize, sortBy, direction);
//        GetAllProductResponse response = mediator.dispatch(new GetAllProductRequest(paginationQuery));
//
//        PaginationResult<Product> productsPage = response.getProductsPage();
//
//        PaginationResult<ProductDto> productsDtoPage = new PaginationResult<>(
//                productsPage.getContent().stream().map(productMapper::mapToProductDto).toList()
//                , productsPage.getPage(),
//                productsPage.getSize(),
//                productsPage.getTotalPages(),
//                productsPage.getTotalElements());
//
//        return ResponseEntity.ok(productsDtoPage);
//    }


    /*
     * ResponseEntity tiene el scope mientras vive la peticion hasta que retorna
     * posteriormente se elimina en el garbage collector
     *
     * required= false
     *
     * INDICA QUE PUEDEN O NO VENIR LOS PARAMETROS ESPECIFICADOS
     *
     * DE ESTA FORMA SE PUEDE PODER OFRECER PAGINACION DESDE LA API CON LOS SIGUIENTES ATRIBUTOS:
     *
     * PARA EVITAR BUSCAR LA INFORMACION DE UN SOLO
     *
     * TAMBIEN SE PUEDEN AGREGAR FILTRADOS DINAMICOS (ESTO OFRECE PAGINACION DINAMICA CON PARAMETROS SEGUN LOS PIDA EL CLIENTE)
     *
     * */
    @Operation(summary = "Get all products", description = "Get all products")
    @GetMapping("")
    public ResponseEntity<PaginationResult<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax
    ) {

        log.info("Getting all products");

        PaginationQuery paginationQuery = new PaginationQuery(pageNumber, pageSize, sortBy, direction);

        ProductFilter productFilter = new ProductFilter(name, description, priceMin, priceMax);

        GetAllProductResponse response = mediator.dispatch(new GetAllProductRequest(paginationQuery, productFilter));

        PaginationResult<Product> productsPage = response.getProductsPage();

        PaginationResult<ProductDto> productsDtoPage = new PaginationResult<>(
                productsPage.getContent().stream().map(productMapper::mapToProductDto).toList()
                , productsPage.getPage(),
                productsPage.getSize(),
                productsPage.getTotalPages(),
                productsPage.getTotalElements());

        return ResponseEntity.ok(productsDtoPage);
    }

    @Operation(summary = "Get product by id", description = "Get product by id")
    @GetMapping("/{id}")

    /*
     * DECORADOR QUE PERMITE RESTRINGIR UN ENDPOINT PARA CIERTO ROL DE USUARIO
     * */
    @PreAuthorize("hasAuthority('ADMIN')")
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

        log.info("Saving product");

        /*PERMITE MAPEAR EL DTO QUE VIENE EN EL REQUEST HACIA EL HANDLER*/
        CreateProductRequest request = productMapper.mapToCreateProductRequest(product);

        CreateProductResponse response = mediator.dispatch(request);

        Product productResponse = response.getProduct();

        log.info("Product with id {} was saved", productResponse.getId());

        return ResponseEntity.created(URI.create("/api/v1/products/".concat(productResponse.getId().toString()))).build();
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
    /*
     * (RECUERDO PARA HACER PETICIONES MULTIPART)
     * */
//    @Operation(summary = "Update product", description = "Update product")
//    @PutMapping
//    public ResponseEntity<Void> updateProduct(@ModelAttribute @Valid UpdateProductDto product) {
//
//        log.info("Updating product with id {}", product.getId());
//
//        /*PERMITE MAPEAR EL DTO QUE VIENE EN EL REQUEST HACIA EL HANDLER*/
//        UpdateProductRequest request = productMapper.mapToUpdateProductRequest(product);
//        mediator.dispatch(request);
//
//        log.info("Product with id {} was updated", product.getId());
//        return ResponseEntity.noContent().build();
//    }

    @Operation(summary = "Update product", description = "Update product")
    @PutMapping
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid UpdateProductDto product) {

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
