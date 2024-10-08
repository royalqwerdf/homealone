package com.elice.homealone.global.crawler;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeMongoRepository extends MongoRepository<RecipeRequest, String> {
    @Query("{'crawled_at': {$gt: ?0}}")
    List<RecipeRequest> findAllWithCreatedDateAfter(Date date);
}
