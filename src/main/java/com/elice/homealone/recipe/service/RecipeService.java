package com.elice.homealone.recipe.service;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.homealoneException;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.recipe.dto.RecipePageDto;
import com.elice.homealone.recipe.dto.RecipeResponseDto;
import com.elice.homealone.recipe.repository.RecipeRepository;
import com.elice.homealone.recipe.dto.RecipeDetailDto;
import com.elice.homealone.recipe.dto.RecipeIngredientDto;
import com.elice.homealone.recipe.dto.RecipeRegisterDto;
import com.elice.homealone.recipe.entity.Recipe;
import com.elice.homealone.tag.Service.PostTagService;
import com.elice.homealone.tag.dto.PostTagDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeImageService recipeImageService;
    private final RecipeDetailService recipeDetailService;
    private final RecipeIngredientService recipeIngredientService;
    private final MemberService memberService;
    private final PostTagService postTagService;

    // 레시피 등록
    @Transactional
    public RecipeResponseDto createRecipe(RecipeRegisterDto registerDto) {

        // 임시 멤버 생성
        Member testMember = memberService.findByEmail("john.doe@example.com");

        // 레시피 dto를 통해 기본 레시피 엔티티를 생성
        Recipe recipe = registerDto.toBaseEntity(testMember);
        recipeRepository.save(recipe);

        // 레시피 dto 이미지 리스트로 레시피 이미지 생성 후 레시피 엔티티에 추가
        List<String> imageUrls = registerDto.getImageUrls();
        if(imageUrls != null) {
            for(String url : imageUrls) {
                recipe.addImage(recipeImageService.createImage(url));
            }
        }

        // 레시피 dto 재료 리스트를 통해 레시피 재료 생성 후 레시피 엔티티에 추가
        List<RecipeIngredientDto> ingredientDtos = registerDto.getIngredientDtos();
        if(ingredientDtos != null){
            for(RecipeIngredientDto ingredientDto : ingredientDtos) {
                recipe.addIngredients(recipeIngredientService.createRecipeIngredient(ingredientDto));
            }
        }

        // 레시피 dto 디테일 리스트로 레시피 디테일 생성 후 레시피 엔티티에 추가
        Optional.ofNullable(registerDto.getDetailDtos())
            .ifPresent(detailDtos -> detailDtos.stream()
                .map(recipeDetailService::createRecipeDetail)
                .forEach(recipe::addDetail));

        // 태그 리스트로 태그 생성 후 레시피 엔티티(포스트 엔티티) 에 추가
        Optional.ofNullable(registerDto.getPostTagDtos())
            .ifPresent(tagDtos -> tagDtos.stream()
                .map(postTagService::createPostTag)
                .forEach(recipe::addTag));

        return recipe.toResponseDto();
    }

    // 레시피 리스트 전체 조회
    public Page<RecipePageDto> findAll(Pageable pageable) {
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);
        return recipePage.map(Recipe::toPageDto);
    }

    // 레시피 리스트 제목으로 조회
    public Page<RecipePageDto> findByTitle(Pageable pageable, String title) {
        Page<Recipe> recipePage = recipeRepository.findByTitleContaining(pageable, title);
        return recipePage.map(Recipe::toPageDto);
    }

    // 레시피 리스트 내용으로 조회
    public Page<RecipePageDto> findByDescription(Pageable pageable, String description) {
        Page<Recipe> recipePage = recipeRepository.findByDescriptionContaining(pageable, description);
        return recipePage.map(Recipe::toPageDto);
    }

    // 레시피 상세 조회
    public RecipeResponseDto findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(()-> new homealoneException(ErrorCode.RECIPE_NOT_FOUND));
        return recipe.toResponseDto();
    }

    // 레시피 삭제
    @Transactional
    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(()-> new homealoneException(ErrorCode.RECIPE_NOT_FOUND));
        recipeRepository.delete(recipe);
    }

}
