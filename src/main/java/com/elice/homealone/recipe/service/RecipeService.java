package com.elice.homealone.recipe.service;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.like.entity.Like;
import com.elice.homealone.like.service.LikeService;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.AuthService;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.post.entity.Post;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeImageService recipeImageService;
    private final RecipeDetailService recipeDetailService;
    private final RecipeIngredientService recipeIngredientService;
    private final PostTagService postTagService;
    private final AuthService authService;

    private final LikeService likeService;

    // 레시피 등록
    @Transactional
    public RecipeResponseDto createRecipe(Member member, RecipeRequestDto requestDto) {
        if (member == null) {
            throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        // 레시피 dto를 통해 기본 레시피 엔티티를 생성
        try {
            Recipe recipe = requestDto.toBaseEntity(member);
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
        List<Recipe> recipes = recipeRepository.findRecipes(pageable, userId, title, description, tags);
        Page<Recipe> recipePage = PageableExecutionUtils.getPage(
            recipes,
            pageable,
            () -> recipeRepository.countRecipes(userId, title, description, tags)
        );

        Member member = authService.getMember();
        if(member == null) {
            return recipePage.map(Recipe::toPageDto);
        }
        // List<Recipe> -> List<Post>
        List<Post> posts = recipes.stream()
            .map(post -> (Post) post)
            .toList();

        List<Like> likes = likeService.findLikesByMemberAndPostIn(member, posts);
        Set<Long> likedRecipeIds = likes.stream()
            .map(like -> like.getPost().getId())
            .collect(Collectors.toSet());

        return recipePage.map(recipe -> {
            RecipePageDto pageDto = recipe.toPageDto();
            pageDto.setLikeByCurrentUser(likedRecipeIds.contains(recipe.getId()));
            return pageDto;
        });
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
    public void deleteRecipe(Member member, Long id) {
        if (member == null) {
            throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(()-> new HomealoneException(ErrorCode.RECIPE_NOT_FOUND));
        recipeRepository.delete(recipe);
    }

    // 레시피 업데이트
    @Transactional
    public RecipeResponseDto patchRecipe(Member member, Long id, RecipeRequestDto requestDto) {
        if (member == null) {
            throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }

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
        recipeDetailService.deleteDetailByRecipe(recipe);
        List<RecipeDetailDto> detailDtos = requestDto.getDetails();
        recipeDetailService.addRecipeDetails(recipe, detailDtos);

        Recipe updatedRecipe;
        updatedRecipe = recipeRepository.saveAndFlush(recipe);

        return updatedRecipe.toResponseDto();
    }
}
