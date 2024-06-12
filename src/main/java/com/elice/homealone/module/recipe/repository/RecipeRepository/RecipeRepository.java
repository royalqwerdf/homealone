package com.elice.homealone.module.recipe.repository.RecipeRepository;

import com.elice.homealone.module.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryCustom {

}