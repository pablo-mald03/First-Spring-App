package com.springcourse.expert.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/*
 * SE DEFINE LA CLASE COMO UN ADAPTADOR UNIVERSAL DE LO QUE SE VA A RETORNAR COMO RESULTADOS PAGINADOS
 *
 * ESTE ADAPTADOR SE DEFINE COMO GENERICO YA QUE SERA UTILIZADO EN VARIAS CLASES
 * */
@Data
@AllArgsConstructor
public class PaginationResult<T> {

    private List<T> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}
