package com.elice.homealone.recipe.repository;

import com.elice.homealone.recipe.entity.RecipeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {

}
