package com.elice.homealone.module.recipe.repository.RecipeIngredientRepository;

import com.elice.homealone.module.recipe.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long>,
    RecipeIngredientRepositoryCustom {

}
