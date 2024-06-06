package com.elice.homealone.recipe.repository.RecipeImageRepository;

import com.elice.homealone.recipe.entity.Recipe;

public interface RecipeImageRepositoryCustom {
    void deleteByRecipe(Recipe recipe);
}
