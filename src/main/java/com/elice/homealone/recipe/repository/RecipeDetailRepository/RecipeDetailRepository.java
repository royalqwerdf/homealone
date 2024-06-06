package com.elice.homealone.recipe.repository.RecipeDetailRepository;

import com.elice.homealone.recipe.entity.RecipeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeDetailRepository extends JpaRepository<RecipeDetail, Long>, RecipeDetailRepositoryCustom {

}
