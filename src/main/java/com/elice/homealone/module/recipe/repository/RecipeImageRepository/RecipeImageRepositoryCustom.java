package com.elice.homealone.module.recipe.repository.RecipeImageRepository;

import com.elice.homealone.module.recipe.entity.Recipe;

public interface RecipeImageRepositoryCustom {
    void deleteByRecipe(Recipe recipe);
}
