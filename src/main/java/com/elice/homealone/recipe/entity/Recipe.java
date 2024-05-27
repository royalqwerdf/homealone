package com.elice.homealone.recipe.entity;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.recipe.dto.RecipeDetailDto;
import com.elice.homealone.recipe.dto.RecipeIngredientDto;
import com.elice.homealone.recipe.dto.RecipePageDto;
import com.elice.homealone.recipe.dto.RecipeResponseDto;
import com.elice.homealone.recipe.enums.Cuisine;
import com.elice.homealone.recipe.enums.RecipeTime;
import com.elice.homealone.recipe.enums.RecipeType;
import com.elice.homealone.tag.dto.PostTagDto;
import com.elice.homealone.tag.entity.PostTag;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeImage> images = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeDetail> details = new ArrayList<>();

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

        List<PostTagDto> tagDtos = getTags().stream()
            .map(PostTag::toDto)
            .toList();

        return RecipeResponseDto.builder()
            .id(this.getId())
            .title(title)
            .description(description)
            .portions(portions)
            .recipeType(recipeType)
            .recipeTime(recipeTime)
            .cuisine(cuisine)
            .imageUrls(imageUrls)
            .ingredientDtos(ingredientDtos)
            .detailDtos(detailDtos)
            .postTagDtos(tagDtos)
            .build();
    }

    public RecipePageDto toPageDto() {
        String imageUrl = null;
        if(images != null){
            imageUrl = images.get(0).getImageUrl();
        }

        return RecipePageDto.builder()
            .id(this.getId())
            .title(title)
            .description(description)
            .portions(portions)
            .recipeType(recipeType.getType())
            .recipeTime(recipeTime.getTime())
            .cuisine(cuisine.getCuisine())
            .imageUrl(imageUrl)
            .build();
    }

    public void addImage(RecipeImage image){
        this.images.add(image);
        image.setRecipe(this);
    }

    public void addDetail(RecipeDetail detail) {
        this.details.add(detail);
        detail.setRecipe(this);
    }

    public void addIngredients(RecipeIngredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }
}

