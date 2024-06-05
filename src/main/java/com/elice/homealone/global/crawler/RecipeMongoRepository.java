package com.elice.homealone.global.crawler;

import com.elice.homealone.recipe.dto.RecipeRequestDto;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RecipeMongoRepository extends MongoRepository<RecipeRequest, String> {

}
