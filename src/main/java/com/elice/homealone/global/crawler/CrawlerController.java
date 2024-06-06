package com.elice.homealone.global.crawler;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.MemberService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class CrawlerController {

    private final CrawlerService crawlerService;
    private final MemberService memberService;

    @PostMapping("/saverecipe/{date}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> importRecipeData(@AuthenticationPrincipal Member member,  @PathVariable String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date parsedDate;
        try {
            parsedDate = formatter.parse(date);
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        crawlerService.loadFromMongoAndSaveRecipe(memberService.findById(member.getId()), parsedDate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
