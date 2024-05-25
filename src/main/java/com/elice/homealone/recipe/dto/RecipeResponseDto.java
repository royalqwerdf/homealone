package com.elice.homealone.recipe.dto;

import com.elice.homealone.recipe.entity.Recipe;
import com.elice.homealone.recipe.enums.Cuisine;
import com.elice.homealone.recipe.enums.RecipeTime;
import com.elice.homealone.recipe.enums.RecipeType;
import com.elice.homealone.tag.dto.TagDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipeResponseDto {

    private Long id;

    private String title;
    private String description;
    private int portions;
    private RecipeType recipeType;
    private RecipeTime recipeTime;
    private Cuisine cuisine;

    private List<String> imageUrls;
    private List<RecipeIngredientDto> ingredientDtos;
    private List<RecipeDetailDto> detailDtos;
    private List<TagDto> tagDtos;

    @Builder
    public RecipeResponseDto(Long id, String title, String description, int portions, RecipeType recipeType, RecipeTime recipeTime, Cuisine cuisine, List<String> imageUrls, List<RecipeIngredientDto> ingredientDtos, List<RecipeDetailDto> detailDtos, List<TagDto> tagDtos) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.portions = portions;
        this.recipeType = recipeType;
        this.recipeTime = recipeTime;
        this.cuisine = cuisine;
        this.imageUrls = imageUrls;
        this.ingredientDtos = ingredientDtos;
        this.detailDtos = detailDtos;
        this.tagDtos = tagDtos;
    }
}
