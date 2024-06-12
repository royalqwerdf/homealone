package com.elice.homealone.module.recipe.repository.RecipeRepository;

import com.elice.homealone.module.recipe.entity.Recipe;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface RecipeRepositoryCustom {
    List<Recipe> findRecipes(
        Pageable pageable,
        Long memberId,
        String title,
        String description,
        List<String> tags
    );

    public Long countRecipes(
        Long memberId,
        String title,
        String description,
        List<String> tags);
}
