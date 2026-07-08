package com.springcourse.expert.product.infrastructure.api.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/*
 * EL DTO PERMITE GENERAR LOGICA DE VALIDACIONES Y PERMITE PASAR LA INFORMACION A UNA ENTIDAD
 *
 *
 * USAR LOMBOK EN LOS DTO ES MAS COMODO PARA AHORRAR CODIGO
 * AUNQUE SI SE QUIERE SER MAS ESPECIFICO ES MEJOR USAR @ por separado para usar campos justos y necesarios
 * */
@Data
public class UpdateProductDto {


    private Long id;
    /*Valida que no hayan espacios y que tenga mas de algun campo*/
    @NotBlank(message = "El nombre es requerido")
    private String name;
    /*Se puede definir la longitud de caracter*/
    @Length(min = 10, max = 255, message = "La descripcion debe tener entre 10 y 255 caracteres")
    private String description;

    /*Se puede definir un valor minimo
     *
     * inclusive = false: El valor es valido excluido del rango inicial
     *
     * inclusive = true: El valor es incluido dentro del intervalo
     * */
    @DecimalMin(value = "0.01", inclusive = false, message = "El precio debe ser mayor a 0")
    @DecimalMax(value = "9999.999", inclusive = false, message = "El precio debe ser menor a 9999.999")
    private Double price;

    /*Para poder aceptar un archivo se usa la interface MultipartFile
     *
     * INDICA UN ARCHIVO MULTIPART QUE VIENE EN EL REQUEST
     *
     * LA NOTACION:
     * @Size(max = 1024000, message = "")
     *
     * max = 1024000: EL TAMANIO SE ESPECIFICA EN BYTES
     *
     * PERO NO NECESARIAMENTE DE UN ARCHIVO. SINO PARA ENTRADAS
     *
     * */

    //CAMPO MODIFICADO POR EVITAR LAS PETICIONES MULTIPART
    //private MultipartFile file;
    @NotBlank(message = "El proveedor es requerido")
    private String provider;

    private ReviewDto review;

    private Long categoryId;
}
