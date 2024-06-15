package com.elice.homealone.module.recipe.repository.RecipeRepository;

import com.elice.homealone.module.recipe.entity.Recipe;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface RecipeRepositoryCustom {
    List<Recipe> findRecipes(
        Pageable pageable,
        String all,
        Long memberId,
        String userName,
        String title,
        String description,
        List<String> tags
    );

    public Long countRecipes(
        String all,
        Long memberId,
        String userName,
        String title,
        String description,
        List<String> tags);
}
