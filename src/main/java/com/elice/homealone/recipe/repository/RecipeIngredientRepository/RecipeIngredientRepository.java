package com.elice.homealone.recipe.repository.RecipeIngredientRepository;

import com.elice.homealone.recipe.entity.Recipe;
import com.elice.homealone.recipe.entity.RecipeIngredient;
import com.elice.homealone.recipe.repository.RecipeRepository.RecipeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long>,
    RecipeIngredientRepositoryCustom {

}
