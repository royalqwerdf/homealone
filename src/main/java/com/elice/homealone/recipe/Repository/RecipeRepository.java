package com.elice.homealone.recipe.Repository;

import com.elice.homealone.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
