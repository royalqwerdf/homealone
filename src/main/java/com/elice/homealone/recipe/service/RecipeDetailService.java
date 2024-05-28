package com.elice.homealone.recipe.service;

import com.elice.homealone.recipe.repository.RecipeDetailRepository;
import com.elice.homealone.recipe.dto.RecipeDetailDto;
import com.elice.homealone.recipe.entity.RecipeDetail;
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
}
