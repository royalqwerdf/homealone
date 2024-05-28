package com.elice.homealone.recipe.dto;

import com.elice.homealone.recipe.entity.RecipeImage;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipeImageDto {

    private Long id;
    private String fileName;
    private String imageUrl;

    @Builder
    public RecipeImageDto(Long id, String fileName, String url) {
        this.id = id;
        this.fileName = fileName;
        this.imageUrl = url;
    }
}
