package com.elice.homealone.recipe.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipeDetailDto {

    private Long id;
    private String description;
    private String imageUrl;

    @Builder
    public RecipeDetailDto(Long id, String description, String imageUrl) {
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
