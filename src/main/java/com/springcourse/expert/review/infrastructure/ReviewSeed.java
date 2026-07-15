package com.springcourse.expert.review.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReviewSeed {
    private String comment;
    private Integer score;
    @JsonProperty("product_id")
    private Long productId;

}
