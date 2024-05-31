package com.elice.homealone.recipe.repository.RecipeRepository;

import com.elice.homealone.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryCustom {

}