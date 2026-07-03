package com.springcourse.expert.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationQuery {
    private int page;
    private int size;
    private String sortBy;
    private String direction;
}
