package com.elice.homealone.module.recipe.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
