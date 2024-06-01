package com.elice.homealone.recipe.repository.RecipeIngredientRepository;

import com.elice.homealone.recipe.entity.Recipe;

public interface RecipeIngredientRepositoryCustom {
    void deleteByRecipe(Recipe recipe);
}
