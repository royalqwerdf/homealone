package com.elice.homealone.recipe.service;

import com.elice.homealone.recipe.dto.RecipeImageDto;
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

    public RecipeImage createImage(RecipeImageDto imageDto){
        RecipeImage recipeImage = RecipeImage.builder()
            .fileName(imageDto.getFileName())
            .imageUrl(imageDto.getImageUrl())
            .build();

        recipeImageRepository.save(recipeImage);

        return recipeImage;
    }
}
