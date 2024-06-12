package com.elice.homealone.module.recipe.service;

import com.elice.homealone.module.recipe.entity.Recipe;
import com.elice.homealone.module.recipe.entity.RecipeImage;
import com.elice.homealone.module.recipe.dto.RecipeImageDto;
import com.elice.homealone.module.recipe.repository.RecipeImageRepository.RecipeImageRepository;

import java.util.List;
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

    public void deleteImage(Long id) {
        recipeImageRepository.deleteById(id);
    }

    public void deleteImageByRecipe(Recipe recipe) {
        recipeImageRepository.deleteByRecipe(recipe);
        recipe.getImages().clear();
    }

    public void addRecipeImages(Recipe recipe, List<RecipeImageDto> recipeImageDtos) {
        if(recipeImageDtos != null) {
            for(RecipeImageDto imageDto : recipeImageDtos) {
                recipe.addImage(createImage(imageDto));
            }
        }
    }
}
