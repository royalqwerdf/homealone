package com.elice.homealone.module.recipe.repository.RecipeImageRepository;

import com.elice.homealone.module.recipe.entity.RecipeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long>,
    RecipeImageRepositoryCustom {
}
