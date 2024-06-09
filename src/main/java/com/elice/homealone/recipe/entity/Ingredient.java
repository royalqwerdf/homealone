package com.elice.homealone.recipe.entity;


import com.elice.homealone.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Ingredient extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    @Builder
    public Ingredient(String name) {this.name = name;}

    public void addIngredient(RecipeIngredient recipeIngredient) {
        this.ingredients.add(recipeIngredient);
        recipeIngredient.setIngredient(this);
    }
}
