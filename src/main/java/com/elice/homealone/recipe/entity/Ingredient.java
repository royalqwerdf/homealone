package com.elice.homealone.recipe.entity;

import com.elice.homealone.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Ingredient extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredient> ingredients;

    @Builder
    public Ingredient(String name) {this.name = name;}

    public void addIngredient(RecipeIngredient recipeIngredient) {
        this.ingredients.add(recipeIngredient);
        recipeIngredient.setIngredient(this);
    }

}
