package com.elice.homealone.recipe.entity;

import com.elice.homealone.common.BaseEntity;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.recipe.enums.RecipeCuisine;
import com.elice.homealone.recipe.enums.RecipeTime;
import com.elice.homealone.recipe.enums.RecipeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

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
    private RecipeCuisine recipeCuisine;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeImage> images;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeTagMap> recipeTags;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> recipeIngredients;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeDetail> details;
}
