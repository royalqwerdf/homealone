package com.elice.homealone;

import com.elice.homealone.recipe.dto.RecipeRegisterDto;
import com.elice.homealone.recipe.enums.RecipeTime;
import com.elice.homealone.recipe.enums.RecipeType;
import com.elice.homealone.recipe.service.RecipeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {

//    private final RecipeService recipeService;
//
//    @PostConstruct
//    public void init() {
//        RecipeRegisterDto registerDto = RecipeRegisterDto.builder()
//            .title("Sample Recipe")
//            .description("this is a sample recipe")
//            .portions(3)
//            .recipeType()
//            .recipeTime()
//            .cuisine()
//            .build()
//    }
}
