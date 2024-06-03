package com.elice.homealone.recipe.controller;

import com.elice.homealone.recipe.dto.RecipePageDto;
import com.elice.homealone.recipe.dto.RecipeRequestDto;
import com.elice.homealone.recipe.dto.RecipeResponseDto;
import com.elice.homealone.recipe.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    // 레시피 등록
    @PostMapping
    public ResponseEntity<RecipeResponseDto> createRecipe(HttpServletRequest request, @RequestBody RecipeRequestDto requestDto) {
        RecipeResponseDto responseDto = recipeService.createRecipe(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 레시피 페이지 조회
    @GetMapping
    public ResponseEntity<Page<RecipePageDto>> getRecipe(Pageable pageable,
        @RequestParam(required = false) String userId,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) List<String> tags
    ) {
        Page<RecipePageDto> pageDtos = recipeService.findRecipes(pageable, userId, title, description, tags);

        return new ResponseEntity<>(pageDtos, HttpStatus.OK);
    }

    // 레시피 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> getRecipeById(@PathVariable Long id) {
        RecipeResponseDto recipe = recipeService.findById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    // 레시피 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 레시피 수정
    @PatchMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> patchRecipe(@PathVariable Long id, @RequestBody RecipeRequestDto requestDto) {
        RecipeResponseDto patchedRecipe = recipeService.patchRecipe(id, requestDto);
        return new ResponseEntity<>(patchedRecipe, HttpStatus.OK);
    }

    // TODO : 트렌드 레시피 추천 api 필요
}

