package com.elice.homealone.recipe.service;

import com.elice.homealone.recipe.repository.RecipeImageRepository;
import com.elice.homealone.recipe.entity.RecipeImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeImageService {

    private final RecipeImageRepository recipeImageRepository;

    public RecipeImage createImage(String imageUrl){
        RecipeImage recipeImage = RecipeImage.builder()
            .imageUrl(imageUrl)
            .build();

        recipeImageRepository.save(recipeImage);

        return recipeImage;
    }
}
