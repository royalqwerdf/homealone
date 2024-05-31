package com.elice.homealone.recipe.repository.RecipeImageRepository;

import com.elice.homealone.recipe.entity.QRecipeImage;
import com.elice.homealone.recipe.entity.RecipeImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long>,
    RecipeImageRepositoryCustom {
}
