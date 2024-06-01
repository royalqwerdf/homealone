package com.elice.homealone.recipe.repository.RecipeDetailRepository;

import com.elice.homealone.recipe.entity.QRecipeDetail;
import com.elice.homealone.recipe.entity.Recipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeDetailRepositoryImpl implements  RecipeDetailRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteByRecipe(Recipe recipe) {
        QRecipeDetail qRecipeDetail = QRecipeDetail.recipeDetail;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        queryFactory.delete(qRecipeDetail)
            .where(qRecipeDetail.recipe.id.eq(recipe.getId()))
            .execute();
    }
}
