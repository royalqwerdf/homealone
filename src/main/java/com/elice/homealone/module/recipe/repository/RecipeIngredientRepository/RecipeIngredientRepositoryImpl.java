package com.elice.homealone.module.recipe.repository.RecipeIngredientRepository;

import com.elice.homealone.module.recipe.entity.QRecipeIngredient;
import com.elice.homealone.module.recipe.entity.Recipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeIngredientRepositoryImpl implements RecipeIngredientRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteByRecipe(Recipe recipe) {
        QRecipeIngredient qRecipeIngredient = QRecipeIngredient.recipeIngredient;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        queryFactory.delete(qRecipeIngredient)
            .where(qRecipeIngredient.recipe.id.eq(recipe.getId()))
            .execute();
    }
}
