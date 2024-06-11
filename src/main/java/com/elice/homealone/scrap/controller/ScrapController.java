package com.elice.homealone.scrap.controller;

import com.elice.homealone.scrap.dto.ScrapReqDto;
import com.elice.homealone.scrap.dto.ScrapResDto;
import com.elice.homealone.scrap.service.ScrapService;
import com.elice.homealone.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scraps")
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;

//    // 북마크 등록
//    @PostMapping
//    public ResponseEntity<ScrapResDto> createScrap(@AuthenticationPrincipal Member member, @RequestBody ScrapReqDto requestDto) {
//        ScrapResDto resDto = scrapService.createScrap(requestDto, member);
//        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
//    }

    // 게시물 북마크 개수 조회
    @GetMapping("/{postId}/count")
    public ResponseEntity<Long> countScrapsByPostId(@PathVariable Long postId) {
        long scrapCount = scrapService.countScrapsByPostId(postId);
        return new ResponseEntity<>(scrapCount, HttpStatus.OK);
    }

//    // 북마크 취소
//    @DeleteMapping("/{scrapId}")
//    public ResponseEntity<Void> deleteScrap(@PathVariable Long scrapId) {
//        scrapService.deleteScrap(scrapId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    // 북마크 등록 / 삭제
    @PostMapping
    public ResponseEntity<ScrapResDto> createAndDeleteScrap(@RequestBody ScrapReqDto reqDto) {
        ScrapResDto resDto = scrapService.createAndDeleteScrap(reqDto);
        if(resDto==null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }
}
