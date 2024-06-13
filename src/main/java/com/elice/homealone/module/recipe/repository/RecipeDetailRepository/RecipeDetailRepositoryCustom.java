package com.elice.homealone.module.recipe.repository.RecipeDetailRepository;

import com.elice.homealone.module.recipe.entity.Recipe;

public interface RecipeDetailRepositoryCustom {
    void deleteByRecipe(Recipe recipe);

}
