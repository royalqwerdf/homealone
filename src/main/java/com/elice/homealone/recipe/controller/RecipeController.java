package com.elice.homealone.recipe.controller;

import com.elice.homealone.recipe.dto.RecipePageDto;
import com.elice.homealone.recipe.dto.RecipeRegisterDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    // 레시피 등록
    @PostMapping
    public ResponseEntity<RecipeResponseDto> createRecipe(HttpServletRequest request, @RequestBody RecipeRegisterDto requestDto) {
        RecipeResponseDto responseDto = recipeService.createRecipe(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 레시피 페이지 조회
    @GetMapping
    public ResponseEntity<Page<RecipePageDto>> getRecipe(Pageable pageable,
        @RequestParam(required = false) String userId,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) List<String> tags
    ) {
        Page<RecipePageDto> pageDtos = null;
        if(userId != null) {
            // 작성자로 조회
        } else if(search != null) {
            // 통합 검색
        } else if(title != null) {
            pageDtos = recipeService.findByTitle(pageable, title);
        } else if(description != null) {
            pageDtos = recipeService.findByDescription(pageable, description);
        } else if (tags != null) {
            // 태그
        } else {
            pageDtos = recipeService.findAll(pageable);
        }

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
}

