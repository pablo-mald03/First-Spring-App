package com.springcourse.expert.product.infrastructure.api.dto;

import lombok.Data;

/*
 * EL DTO PERMITE GENERAR LOGICA DE VALIDACIONES Y PERMITE PASAR LA INFORMACION A UNA ENTIDAD
 *
 *
 * USAR LOMBOK EN LOS DTO ES MAS COMODO PARA AHORRAR CODIGO
 * AUNQUE SI SE QUIERE SER MAS ESPECIFICO ES MEJOR USAR @ por separado para usar campos justos y necesarios
 * */
@Data
public class ProductDto {

    /*SI ES LA REPRESENTACION DEL OBJETO QUE SE VA A RETORNAR YA NO HACE FALTA VALIDARLO
     * POQRUE PROVIENE DEL BACKEND*/
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;

}
