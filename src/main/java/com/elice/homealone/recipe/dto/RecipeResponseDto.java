package com.elice.homealone.recipe.dto;

import com.elice.homealone.post.dto.PostRelatedDto;
import com.elice.homealone.recipe.enums.Cuisine;
import com.elice.homealone.recipe.enums.RecipeTime;
import com.elice.homealone.recipe.enums.RecipeType;
import com.elice.homealone.tag.dto.PostTagDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RecipeResponseDto {

    private Long id;

    private String title;
    private String description;
    private int portions;
    private RecipeType recipeType;
    private RecipeTime recipeTime;
    private Cuisine cuisine;

    private List<RecipeImageDto> images;
    private List<RecipeIngredientDto> ingredients;
    private List<RecipeDetailDto> details;
    private List<PostTagDto> postTags;

    private Long userId;
    private String userName;

    @Setter
    private PostRelatedDto relatedDto;

    @Builder
    public RecipeResponseDto(
        Long id,
        String title,
        String description,
        int portions,
        RecipeType recipeType,
        RecipeTime recipeTime,
        Cuisine cuisine,
        List<RecipeImageDto> images,
        List<RecipeIngredientDto> ingredients,
        List<RecipeDetailDto> details,
        List<PostTagDto> postTags,
        Long userId,
        String userName,
        PostRelatedDto relatedDto
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.portions = portions;
        this.recipeType = recipeType;
        this.recipeTime = recipeTime;
        this.cuisine = cuisine;
        this.images = images;
        this.ingredients = ingredients;
        this.details = details;
        this.postTags = postTags;
        this.userId = userId;
        this.userName = userName;
        this.relatedDto = relatedDto;
    }
}
