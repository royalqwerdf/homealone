package com.elice.homealone.global.crawler;

import com.elice.homealone.member.dto.request.SignupRequestDTO;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.entity.Role;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.member.service.AuthService;
import com.elice.homealone.recipe.dto.RecipeRequestDto;
import com.elice.homealone.recipe.dto.RecipeResponseDto;
import com.elice.homealone.recipe.entity.Recipe;
import com.elice.homealone.recipe.enums.Cuisine;
import com.elice.homealone.recipe.enums.RecipeType;
import com.elice.homealone.recipe.service.RecipeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.rpc.context.AttributeContext.Auth;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlerService {

    private final RecipeService recipeService;
    private final RecipeMongoRepository recipeMongoRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

/*
    public void loadJsonAndSaveRecipe() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 파일을 읽어와서 RecipeRequestDto 형태로 변환
            List<RecipeRequestDto> recipeRequestDtos = objectMapper.readValue(new File("/data/recipe.json"), new TypeReference<List<RecipeRequestDto>>(){});

            for(RecipeRequestDto requestDto : recipeRequestDtos) {
                recipeService.createRecipe(requestDto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 */

    public void loadFromMongoAndSaveRecipe(Member member,Date date) {
        // MongoDB에서 Recipe 데이터를 읽어옴
        List<RecipeRequest> recipes = recipeMongoRepository.findAllWithCreatedDateAfter(date);

        int totalRecipes = recipes.size();
        int processCount = 0;
        for(RecipeRequest recipe : recipes) {
            // 변환된 RecipeRequestDto를 사용하여 레시피 생성
            RecipeRequestDto requestDto = RecipeRequestDto.from(recipe);
            recipeService.createRecipe(member, requestDto);
            processCount++;
            if(processCount % (totalRecipes / 10) == 0){
                int progress = (int) ((double) processCount / totalRecipes * 100);
                System.out.printf("진행도: %d%%\n", progress);
            }
        }
    }

    public void adminSignUp(SignupRequestDTO signupRequestDTO) {
        String email = signupRequestDTO.getEmail();
        if (!memberRepository.existsByEmail(email)) {
            Member admin = Member.builder()
                .name("관리자")
                .birth(signupRequestDTO.getBirth()) // 관리자 생년월일 예시
                .email(email)
                .address(signupRequestDTO.getAddress())
                .password(passwordEncoder.encode(signupRequestDTO.getPassword())) // 관리자 비밀번호
                .role(Role.ROLE_ADMIN) // 관리자 역할 설정
                .build();
            memberRepository.save(admin);
        }
    }
}
