package com.elice.homealone;

import com.elice.homealone.global.crawler.CrawlerService;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.entity.Role;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.recipe.dto.RecipeDetailDto;
import com.elice.homealone.recipe.dto.RecipeImageDto;
import com.elice.homealone.recipe.dto.RecipeIngredientDto;
import com.elice.homealone.recipe.dto.RecipeRequestDto;
import com.elice.homealone.recipe.enums.Cuisine;
import com.elice.homealone.recipe.enums.RecipeTime;
import com.elice.homealone.recipe.enums.RecipeType;
import com.elice.homealone.recipe.service.RecipeService;
import com.elice.homealone.room.dto.RoomRequestDTO;
import com.elice.homealone.room.service.RoomService;
import com.elice.homealone.tag.dto.PostTagDto;
import com.elice.homealone.talk.Service.TalkService;
import com.elice.homealone.talk.dto.TalkRequestDTO;
import com.elice.homealone.usedtrade.dto.UsedTradeRequestDto;
import com.elice.homealone.usedtrade.entity.UsedTradeImage;
import com.elice.homealone.usedtrade.service.UsedTradeService;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataInit implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecipeService recipeService;
    private final TalkService talkService;
    private final UsedTradeService usedTradeService;
    private final RoomService roomService;

    @Override
    public void run(String... args) throws Exception {
        String email = "geobukseon@homealone.co.kr";
        if (!memberRepository.existsByEmail(email)) {
            Member admin = Member.builder()
                .name("관리자")
                .birth(LocalDate.of(1945, 4, 28)) // 관리자 생년월일 예시
                .email(email)
                .firstAddress("Seoul")
                .secondAddress("광진구")
                .password(passwordEncoder.encode("1234")) // 관리자 비밀번호
                .role(Role.ROLE_ADMIN) // 관리자 역할 설정
                .build();
            memberRepository.save(admin);
        }

        // 테스트 유저 생성
        email = "test1@test.com";
        Member user = null;
        if (!memberRepository.existsByEmail(email)) {
            user = Member.builder()
                .name("유저")
                .birth(LocalDate.of(1945, 4, 28))
                .email(email)
                .firstAddress("Deageon")
                .secondAddress("유성구")
                .password(passwordEncoder.encode("1234"))
                .role(Role.ROLE_USER) // 유저 역할 설정
                .build();
            memberRepository.save(user);
        }

        // 테스트 레시피 생성

//        // 테스트 이미지 생성
//        List<RecipeImageDto> images = new ArrayList<>();
//        images.add(RecipeImageDto.builder()
//            .fileName("image")
//            .url("image1.jpg")
//            .build());
//        images.add(RecipeImageDto.builder()
//            .fileName("image2")
//            .url("image2.jpg")
//            .build());
//
//        // 테스트 재료 생성
//        List<RecipeIngredientDto> ingredients = new ArrayList<>();
//        ingredients.add(RecipeIngredientDto.builder()
//            .name("ingredient1")
//            .quantity("1 cup")
//            .build());
//        ingredients.add(RecipeIngredientDto.builder()
//            .name("ingredient2").
//            quantity("2 tablespoons")
//            .build());
//
//        // 테스트 상세 설명 생성
//        List<RecipeDetailDto> details = new ArrayList<>();
//        details.add(RecipeDetailDto.builder()
//            .fileName("Step 1")
//            .description("Do this")
//            .build());
//        details.add(RecipeDetailDto.builder()
//            .fileName("Step 2")
//            .description("Do that")
//            .build());
//
//        // 테스트 태그 생성
//        List<PostTagDto> postTags = new ArrayList<>();
//        postTags.add(PostTagDto.builder()
//            .tagName("tag1").build());
//        postTags.add(PostTagDto.builder()
//            .tagName("tag2").build());
//
//        RecipeRequestDto recipeReqDto = RecipeRequestDto.builder()
//            .title("테스트 레시피")
//            .description("테스트 레시피 설명")
//            .portions(4)
//            .recipeType(RecipeType.CONDI)
//            .recipeTime(RecipeTime.FIFTEEN)
//            .cuisine(Cuisine.KOREAN)
//            .images(images)
//            .ingredients(ingredients)
//            .details(details)
//            .postTags(postTags)
//            .build();
//        recipeService.createRecipe(user, recipeReqDto);

        // 테스트 혼잣말 생성

//        // 테스트 이미지 생성
//        List<String> imageStrs = new ArrayList<>();
//        imageStrs.add("image1.jpg");
//        imageStrs.add("image2.jpg");
//
//        TalkRequestDTO talkRequestDTO = TalkRequestDTO.builder()
//                .title("테스트 혼잣말")
//                .content("테스트 혼잣말입니다")
//                .tags(postTags)
//                .images(imageStrs)
//                .build();
//        talkService.CreateTalkPost(talkRequestDTO);
//
//        // 테스트 방자랑 생성
//        RoomRequestDTO roomRequestDTO = RoomRequestDTO.builder()
//                .title("테스트 방자랑")
//                .content("테스트 방자랑입니다.")
//                .tags(postTags)
//                .thumbnailUrl("image.jpg")
//                .roomImages(imageStrs)
//                .build();
//        roomService.CreateRoomPost(roomRequestDTO);
//
//        // 테스트 중고거래 생성
//        // 테스트 이미지 생성
//        List<UsedTradeImage> usedImages = new ArrayList<>();
//        usedImages.add(UsedTradeImage.builder()
//                .main(true)
//                .url("image1.jpg")
//                .build());
//
//        UsedTradeRequestDto usedReqDto = UsedTradeRequestDto.builder()
//                .title("테스트 중고거래")
//                .price(25000)
//                .location("서울")
//                .content("테스트 중고거래입니다.")
//                .memberId(1L)
//                .images(usedImages)
//                .build();
//        usedTradeService.createUsedTrade(usedReqDto);
    }
}
