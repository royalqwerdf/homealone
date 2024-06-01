package com.elice.homealone.recipe.repository.RecipeDetailRepository;

import com.elice.homealone.recipe.entity.Recipe;

public interface RecipeDetailRepositoryCustom {
    void deleteByRecipe(Recipe recipe);

}
