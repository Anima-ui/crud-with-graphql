package com.anima.graphql.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInput {
    private String title;
    private Long authorId;
    private String pageCount;
}
