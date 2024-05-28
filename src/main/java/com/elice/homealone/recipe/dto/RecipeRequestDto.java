package com.elice.homealone.recipe.dto;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.recipe.entity.Recipe;
import com.elice.homealone.recipe.enums.Cuisine;
import com.elice.homealone.recipe.enums.RecipeTime;
import com.elice.homealone.recipe.enums.RecipeType;
import com.elice.homealone.tag.dto.PostTagDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecipeRequestDto {
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

    @Builder
    public RecipeRequestDto(String title, String description, int portions, RecipeType recipeType, RecipeTime recipeTime, Cuisine cuisine, List<RecipeImageDto> images, List<RecipeIngredientDto> ingredients, List<RecipeDetailDto> details, List<PostTagDto> postTags) {
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
    }

    public Recipe toBaseEntity(Member member) {
        Recipe recipe = Recipe.builder()
            .member(member)
            .title(this.getTitle())
            .description(this.getDescription())
            .portions(this.getPortions())
            .recipeType(this.getRecipeType())
            .recipeTime(this.getRecipeTime())
            .cuisine(this.getCuisine())
            .build();
        return recipe;
    }
}
