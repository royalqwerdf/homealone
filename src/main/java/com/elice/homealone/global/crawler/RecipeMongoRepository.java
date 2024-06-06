package com.elice.homealone.global.crawler;

import com.elice.homealone.recipe.dto.RecipeRequestDto;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface RecipeMongoRepository extends MongoRepository<RecipeRequest, String> {
    @Query("{'crawled_at': {$gt: ?0}}")
    List<RecipeRequest> findAllWithCreatedDateAfter(Date date);
}
