package com.elice.homealone.recipe.entity;

import com.elice.homealone.common.BaseEntity;
import jakarta.persistence.*;

public class RecipeDetailImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "recipeDetailImage")
    private RecipeDetail recipeDetail;
}
