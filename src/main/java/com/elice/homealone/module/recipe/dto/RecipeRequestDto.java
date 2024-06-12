package com.elice.homealone.module.recipe.dto;

import com.elice.homealone.global.crawler.RecipeRequest;
import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.recipe.entity.Recipe;
import com.elice.homealone.module.recipe.enums.Cuisine;
import com.elice.homealone.module.recipe.enums.RecipeTime;
import com.elice.homealone.module.recipe.enums.RecipeType;
import com.elice.homealone.module.tag.dto.PostTagDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private int view = 0;

    @Builder
    public RecipeRequestDto(
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
        int view) {

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
        this.view = view;
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
            .view(this.view)
            .build();
        return recipe;
    }

    public static RecipeRequestDto from(RecipeRequest recipeRequest) {
        return RecipeRequestDto.builder()
            .title(recipeRequest.getTitle())
            .description(recipeRequest.getDescription())
            .portions(recipeRequest.getPortions() == null ? 2 : recipeRequest.getPortions())
            .recipeType(recipeRequest.getRecipeType())
            .recipeTime(recipeRequest.getRecipeTime())
            .cuisine(recipeRequest.getCuisine())
            .images(recipeRequest.getImages())
            .ingredients(recipeRequest.getIngredients())
            .details(recipeRequest.getDetails())
            .postTags(recipeRequest.getPostTags())
            .view(recipeRequest.getView())
            .build();
    }
}
