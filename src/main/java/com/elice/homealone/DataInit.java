package com.elice.homealone;

import com.elice.homealone.global.crawler.CrawlerService;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.entity.Role;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.recipe.service.RecipeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

    private final CrawlerService crawlerService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        //crawlerService.loadJsonAndSaveRecipe();

        String email = "geobukseon@homealone.co.kr";
        if (!memberRepository.existsByEmail(email)) {
            Member admin = Member.builder()
                    .name("관리자")
                    .birth(LocalDate.of(1945, 4, 28)) // 관리자 생년월일 예시
                    .email(email)
                    .address("Seoul")
                    .password(passwordEncoder.encode("1234")) // 관리자 비밀번호
                    .role(Role.ROLE_ADMIN) // 관리자 역할 설정
                    .build();
            memberRepository.save(admin);
        }

        //crawlerService.loadFromMongoAndSaveRecipe();
    }
}
