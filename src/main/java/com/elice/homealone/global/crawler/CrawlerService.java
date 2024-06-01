package com.elice.homealone.global.crawler;

import com.elice.homealone.recipe.dto.RecipeRequestDto;
import com.elice.homealone.recipe.dto.RecipeResponseDto;
import com.elice.homealone.recipe.enums.Cuisine;
import com.elice.homealone.recipe.enums.RecipeType;
import com.elice.homealone.recipe.service.RecipeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlerService {

    private final RecipeService recipeService;

    public void loadJsonAndSaveRecipe() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 파일을 읽어와서 RecipeRequestDto 형태로 변환
            List<RecipeRequestDto> recipeRequestDtos = objectMapper.readValue(new File("python_workspace/data/recipe.json"), new TypeReference<List<RecipeRequestDto>>(){});

            for(RecipeRequestDto requestDto : recipeRequestDtos) {
                recipeService.createRecipe(requestDto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
