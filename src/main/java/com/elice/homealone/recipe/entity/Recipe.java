package com.elice.homealone.recipe.entity;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.recipe.dto.RecipeDetailDto;
import com.elice.homealone.recipe.dto.RecipeImageDto;
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
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Recipe extends Post {

    @Column
    @Setter
    private String title;

    @Column(length = 1500)
    @Setter
    private String description;

    @Column
    @Setter
    private int portions;

    @Column
    @Setter
    private RecipeType recipeType;
    @Column
    @Setter
    private RecipeTime recipeTime;
    @Column
    @Setter
    private Cuisine cuisine;

    @Column
    @Setter
    private int view = 0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeImage> images = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeDetail> details = new ArrayList<>();

    @Builder
    public Recipe(
        Member member,
        String title,
        String description,
        int portions,
        RecipeType recipeType,
        RecipeTime recipeTime,
        Cuisine cuisine,
        int view) {
        // Post
        super(member, Type.RECIPE);

        // Recipe
        this.title = title;
        this.description = description;
        this.portions = (portions == 0) ? 1 : portions;
        this.recipeType = (recipeType == null)? RecipeType.ETC : recipeType;
        this.recipeTime = (recipeTime == null)?RecipeTime.THIRTY : recipeTime;
        this.cuisine = (cuisine==null)?Cuisine.ETC:cuisine;
        this.view = view;
    }

    // toDto
    public RecipeResponseDto toResponseDto() {
        List<RecipeImageDto> imageDtos = images.stream()
            .map(RecipeImage::toDto)
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

        Long userId = this.getMember().getId();
        String userName = this.getMember().getName();

        return RecipeResponseDto.builder()
            .id(this.getId())
            .title(title)
            .description(description)
            .portions(portions)
            .recipeType(recipeType)
            .recipeTime(recipeTime)
            .cuisine(cuisine)
            .images(imageDtos)
            .ingredients(ingredientDtos)
            .details(detailDtos)
            .postTags(tagDtos)
            .userId(userId)
            .userName(userName)
            .view(view)
            .build();
    }

    public RecipePageDto toPageDto() {
        String imageUrl = null;
        if(images != null){
            imageUrl = images.get(0).getImageUrl();
        }
        Long userId = this.getMember().getId();
        String userName = this.getMember().getName();

        // 라이크를 찾는 메소드


        return RecipePageDto.builder()
            .id(this.getId())
            .title(title)
            .description(description)
            .portions(portions)
            .recipeType(recipeType.getType())
            .recipeTime(recipeTime.getTime())
            .cuisine(cuisine.getCuisine())
            .imageUrl(imageUrl)
            .userId(userId)
            .userName(userName)
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

    public void addIngredients(RecipeIngredient ingredient)  {
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }
}

