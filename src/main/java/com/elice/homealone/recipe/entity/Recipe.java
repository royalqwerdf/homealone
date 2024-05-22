package com.elice.homealone.recipe.entity;

import com.elice.homealone.post.entity.Post;
import com.elice.homealone.recipe.enums.Cuisine;
import com.elice.homealone.recipe.enums.RecipeTime;
import com.elice.homealone.recipe.enums.RecipeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
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
    private int recipePortions;

    @Column
    private RecipeType recipeType;
    @Column
    private RecipeTime recipeTime;
    @Column
    private Cuisine cuisine;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<RecipeImage> images;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<RecipeIngredient> recipeIngredients;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<RecipeDetail> details;
}
