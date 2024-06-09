package com.elice.homealone.recipe.service;

import com.elice.homealone.recipe.entity.Recipe;
import com.elice.homealone.recipe.repository.RecipeDetailRepository.RecipeDetailRepository;
import com.elice.homealone.recipe.dto.RecipeDetailDto;
import com.elice.homealone.recipe.entity.RecipeDetail;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeDetailService {

    private final RecipeDetailRepository recipeDetailRepository;

    public RecipeDetail createRecipeDetail(RecipeDetailDto recipeDetailDto) {
        RecipeDetail recipeDetail = RecipeDetail.builder()
            .description(recipeDetailDto.getDescription())
            .fileName(recipeDetailDto.getFileName())
            .imageUrl(recipeDetailDto.getImageUrl())
            .build();

        recipeDetailRepository.save(recipeDetail);
        return recipeDetail;
    }

    public void deleteDetailByRecipe(Recipe recipe){
        recipeDetailRepository.deleteByRecipe(recipe);
    }

    public void deleteDetail(RecipeDetail detail) {
        recipeDetailRepository.delete(detail);
    }

    public void addRecipeDetails(Recipe recipe, List<RecipeDetailDto> recipeDetailDtos) {
        if(recipeDetailDtos != null) {
            for(RecipeDetailDto detailDto : recipeDetailDtos) {
                recipe.addDetail(createRecipeDetail(detailDto));
            }
        }
    }
}
