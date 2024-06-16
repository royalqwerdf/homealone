package com.elice.homealone.module.recipe.repository.RecipeImageRepository;

import com.elice.homealone.module.recipe.entity.QRecipeImage;
import com.elice.homealone.module.recipe.entity.Recipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeImageRepositoryImpl implements RecipeImageRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteByRecipe(Recipe recipe) {
        QRecipeImage qRecipeImage = QRecipeImage.recipeImage;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        queryFactory.delete(qRecipeImage)
            .where(qRecipeImage.recipe.id.eq(recipe.getId()))
            .execute();
    }
}
