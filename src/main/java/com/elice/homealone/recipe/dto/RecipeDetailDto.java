package com.elice.homealone.recipe.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipeDetailDto {

    private Long id;
    private String description;
    private String fileName;
    private String imageUrl;

    @Builder
    public RecipeDetailDto(Long id, String description, String fileName, String imageUrl) {
        this.id = id;
        this.description = description;
        this.fileName = fileName;
        this.imageUrl = imageUrl;
    }
}
