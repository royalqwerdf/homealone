package com.elice.homealone.recipe.entity;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.recipe.dto.RecipeDetailDto;
import com.elice.homealone.recipe.dto.RecipeIngredientDto;
import com.elice.homealone.recipe.dto.RecipeResponseDto;
import com.elice.homealone.recipe.enums.Cuisine;
import com.elice.homealone.recipe.enums.RecipeTime;
import com.elice.homealone.recipe.enums.RecipeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Recipe extends Post {

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private int portions;

    @Column
    private RecipeType recipeType;
    @Column
    private RecipeTime recipeTime;
    @Column
    private Cuisine cuisine;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<RecipeImage> images;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<RecipeIngredient> ingredients;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<RecipeDetail> details;

    @Builder
    public Recipe(Member member, String title, String description, int portions, RecipeType recipeType, RecipeTime recipeTime, Cuisine cuisine) {
        // Post
        super(member, Type.RECIPE);

        // Recipe
        this.title = title;
        this.description = description;
        this.portions = portions;
        this.recipeType = recipeType;
        this.recipeTime = recipeTime;
        this.cuisine = cuisine;
    }

    // toDto
    public RecipeResponseDto toResponseDto() {
        List<String> imageUrls = images.stream()
            .map(RecipeImage::getImageUrl)
            .toList();

        List<RecipeIngredientDto> ingredientDtos = ingredients.stream()
            .map(RecipeIngredient::toDto)
            .toList();

        List<RecipeDetailDto> detailDtos = details.stream()
            .map(RecipeDetail::toDto)
            .toList();

        return RecipeResponseDto.builder()
            .id(this.getId())
            .title(this.title)
            .description(this.description)
            .portions(this.portions)
            .recipeType(this.recipeType)
            .recipeTime(this.recipeTime)
            .cuisine(this.cuisine)
            .imageUrls(imageUrls)
            .ingredientDtos(ingredientDtos)
            .detailDtos(detailDtos)
            .tagDtos(null)
            .build();
    }

    public void addImage(RecipeImage image){
        this.images.add(image);
        image.setRecipe(this);
    }

    public void addDetail(RecipeDetail detail) {
        this.details.add(detail);
    }

    public void addIngredients(RecipeIngredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }
}
