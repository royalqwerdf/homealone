package com.elice.homealone.recipe.dto;

import com.elice.homealone.post.dto.PostRelatedDto;
import com.elice.homealone.post.dto.PostResPageDto;
import com.elice.homealone.recipe.enums.Cuisine;
import com.elice.homealone.recipe.enums.RecipeTime;
import com.elice.homealone.recipe.enums.RecipeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RecipePageDto {
    private Long id;

    private String title;
    private String description;
    private final int portions;
    private String recipeType;
    private int recipeTime;
    private String cuisine;

    private String imageUrl;
    private Long userId;
    private String userName;

    @Setter
    private PostRelatedDto relatedDto;

    private final Integer view;

    @Builder
    public RecipePageDto(
        Long id,
        String title,
        String description,
        int portions,
        String recipeType,
        int recipeTime,
        String cuisine,
        String imageUrl,
        Long userId,
        String userName,
        int view) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.portions = portions;
        this.recipeType = recipeType;
        this.recipeTime = recipeTime;
        this.cuisine = cuisine;

        this.imageUrl = imageUrl;
        this.userId = userId;
        this.userName = userName;

        this.view = view;
    }
}
