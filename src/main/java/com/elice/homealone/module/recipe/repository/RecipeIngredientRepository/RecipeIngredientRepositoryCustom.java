package com.elice.homealone.module.recipe.repository.RecipeIngredientRepository;

import com.elice.homealone.module.recipe.entity.Recipe;

public interface RecipeIngredientRepositoryCustom {
    void deleteByRecipe(Recipe recipe);
}
