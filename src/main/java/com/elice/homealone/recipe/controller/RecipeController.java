package com.elice.homealone.recipe.controller;

import com.elice.homealone.recipe.dto.RecipeRegisterDto;
import com.elice.homealone.recipe.dto.RecipeResponseDto;
import com.elice.homealone.recipe.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    // 레시피 등록
    @PostMapping
    public ResponseEntity<RecipeResponseDto> createRecipe(HttpServletRequest request, @RequestBody RecipeRegisterDto requestDto) {
        RecipeResponseDto responseDto = recipeService.createRecipe(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
