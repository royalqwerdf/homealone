package com.elice.homealone.global.crawler;

import com.elice.homealone.module.recipe.dto.RecipeDetailDto;
import com.elice.homealone.module.recipe.dto.RecipeImageDto;
import com.elice.homealone.module.recipe.dto.RecipeIngredientDto;
import com.elice.homealone.module.recipe.enums.Cuisine;
import com.elice.homealone.module.recipe.enums.RecipeTime;
import com.elice.homealone.module.recipe.enums.RecipeType;
import com.elice.homealone.module.tag.dto.PostTagDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recipe")
@Getter
public class RecipeRequest {
    private String title;
    private String description;
    private Integer portions;
    private RecipeType recipeType;
    private RecipeTime recipeTime;
    private Cuisine cuisine;

    private List<RecipeImageDto> images;
    private List<RecipeIngredientDto> ingredients;
    private List<RecipeDetailDto> details;
    private List<PostTagDto> postTags;

    private int view = 0;

    @Builder
    public RecipeRequest(
        String title,
        String description,
        Integer portions,
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
}
