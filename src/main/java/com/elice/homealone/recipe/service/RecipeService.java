package com.elice.homealone.recipe.service;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.recipe.dto.RecipeDetailDto;
import com.elice.homealone.recipe.dto.RecipeImageDto;
import com.elice.homealone.recipe.dto.RecipePageDto;
import com.elice.homealone.recipe.dto.RecipeResponseDto;

import com.elice.homealone.recipe.entity.RecipeDetail;

import com.elice.homealone.recipe.entity.RecipeIngredient;
import com.elice.homealone.recipe.repository.RecipeRepository.RecipeRepository;
import com.elice.homealone.recipe.dto.RecipeIngredientDto;
import com.elice.homealone.recipe.dto.RecipeRequestDto;
import com.elice.homealone.recipe.entity.Recipe;
import com.elice.homealone.tag.Service.PostTagService;
import jakarta.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public RecipeResponseDto createRecipe(RecipeRequestDto requestDto) {

        // 임시 멤버 생성
        Member testMember = memberService.findByEmail("geobukseon@homealone.co.kr");

        // 레시피 dto를 통해 기본 레시피 엔티티를 생성
        try {
            Recipe recipe = requestDto.toBaseEntity(testMember);
            recipeRepository.save(recipe);

            // 레시피 dto 이미지 리스트로 레시피 이미지 생성 후 레시피 엔티티에 추가
            List<RecipeImageDto> images = requestDto.getImages();
            recipeImageService.addRecipeImages(recipe, images);

            // 레시피 dto 재료 리스트를 통해 레시피 재료 생성 후 레시피 엔티티에 추가
            List<RecipeIngredientDto> ingredientDtos = requestDto.getIngredients();
            if(ingredientDtos != null){
                for(RecipeIngredientDto ingredientDto : ingredientDtos) {
                    recipe.addIngredients(recipeIngredientService.createRecipeIngredient(ingredientDto));
                }
            }

            // 레시피 dto 디테일 리스트로 레시피 디테일 생성 후 레시피 엔티티에 추가
            Optional.ofNullable(requestDto.getDetails())
                .ifPresent(detailDtos -> detailDtos.stream()
                    .map(recipeDetailService::createRecipeDetail)
                    .forEach(recipe::addDetail));

            // 태그 리스트로 태그 생성 후 레시피 엔티티(포스트 엔티티) 에 추가
            Optional.ofNullable(requestDto.getPostTags())
                .ifPresent(tagDtos -> tagDtos.stream()
                    .map(postTagService::createPostTag)
                    .forEach(recipe::addTag));

            return recipe.toResponseDto();

        } catch (Exception e) {
            throw new HomealoneException(ErrorCode.RECIPE_CREATION_FAILED);
        }
    }

    // QueryDsl 레시피 페이지 조회
    public Page<RecipePageDto> findRecipes(
        Pageable pageable,
        String userId,
        String title,
        String description,
        List<String> tags
    ) {
        Page<Recipe> recipePage = recipeRepository.findRecipes(pageable, userId, title, description, tags);
        return recipePage.map(Recipe::toPageDto);
    }

    // 레시피 리스트 전체 조회
    public Page<RecipePageDto> findAll(Pageable pageable) {
        try {
            Page<Recipe> recipePage = recipeRepository.findAll(pageable);
            return recipePage.map(Recipe::toPageDto);
        } catch (Exception e) {
            throw new HomealoneException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 레시피 상세 조회
    public RecipeResponseDto findById(Long id) {
        try {
            Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(()-> new HomealoneException(ErrorCode.RECIPE_NOT_FOUND));
            return recipe.toResponseDto();
        } catch (Exception e) {
            throw new HomealoneException(ErrorCode.BAD_REQUEST);
        }
    }

    // 레시피 삭제
    @Transactional
    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(()-> new HomealoneException(ErrorCode.RECIPE_NOT_FOUND));
        recipeRepository.delete(recipe);
    }

    // 레시피 업데이트
    @Transactional
    public RecipeResponseDto patchRecipe(Long id, RecipeRequestDto requestDto) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(()-> new HomealoneException(ErrorCode.RECIPE_NOT_FOUND));

        // 기본 레시피 수정
        recipe.setTitle(requestDto.getTitle());
        recipe.setDescription(requestDto.getDescription());
        recipe.setPortions(requestDto.getPortions());
        recipe.setRecipeType(requestDto.getRecipeType());
        recipe.setRecipeTime(requestDto.getRecipeTime());
        recipe.setCuisine(requestDto.getCuisine());

        // 연관 관계 수정 (이미지, 재료, 디테일, 태그)

        // 이미지 전체 삭제 후 재생성
        recipeImageService.deleteImageByRecipe(recipe);
        List<RecipeImageDto> imageDtos = requestDto.getImages();
        recipeImageService.addRecipeImages(recipe, imageDtos);

        // 재료 수정
        recipeIngredientService.deleteRecipeIngredientByRecipe(recipe);
        List<RecipeIngredientDto> ingredientDtos = requestDto.getIngredients();
        recipeIngredientService.addRecipeIngredients(recipe, ingredientDtos);

        // 레시피 디테일 수정
        // 레시피와 관련된 레시피 디테일 제거
        recipeDetailService.deleteDetailByRecipe(recipe);
        List<RecipeDetailDto> detailDtos = requestDto.getDetails();
        recipeDetailService.addRecipeDetails(recipe, detailDtos);

        Recipe updatedRecipe;
        updatedRecipe = recipeRepository.saveAndFlush(recipe);

        return updatedRecipe.toResponseDto();
    }

    public void updateRecipeIngredients(Recipe recipe, List<RecipeIngredientDto> updateIngredientDtos) {
        List<Long> updateIngredientIds = updateIngredientDtos.stream()
            .map(RecipeIngredientDto::getId)
            .toList();

        // 기존 재료
        List<RecipeIngredient> ingredientDtos = recipe.getIngredients();

        // 기존 재료 중 업데이트 재료 리스트에 없는 재료를 삭제
        Iterator<RecipeIngredient> iterator = ingredientDtos.iterator();
        while(iterator.hasNext()) {
            RecipeIngredient ingredient = iterator.next();
            if(!updateIngredientIds.contains(ingredient.getId())){
                iterator.remove();
                recipeIngredientService.deleteRecipeIngredient(ingredient);
            }
        }

        // 변경할 재료 중에서 기존 재료 리스트에 없는 재료를 찾아 추가해준다.
        for(RecipeIngredientDto updateIngredientDto : updateIngredientDtos) {
            if(updateIngredientDto.getId() == null){
                RecipeIngredient newIngredient = recipeIngredientService.createRecipeIngredient(updateIngredientDto);
                recipe.addIngredients(newIngredient);
            }
        }
    }

    public void updateRecipeDetails(Recipe recipe, List<RecipeDetailDto> updateRecipeDetailDtos) {
        // 레시피 디테일 전체 삭제
        recipeDetailService.deleteDetailByRecipe(recipe);
    }

    public void deleteDetailsByRecipe(Recipe recipe) {
        List<RecipeDetail> recipeDetails = recipe.getDetails();

        for(RecipeDetail detail : recipeDetails) {
            recipeDetailService.deleteDetail(detail);
        }
    }

}
