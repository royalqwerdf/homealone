package com.elice.homealone.recipe.entity;

import com.elice.homealone.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class RecipeDetailImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String imageUrl;

    @OneToOne(mappedBy = "recipeDetailImage")
    private RecipeDetail recipeDetail;
}
