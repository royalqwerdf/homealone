package com.elice.homealone.module.like.controller;

import com.elice.homealone.module.like.dto.LikeReqDto;
import com.elice.homealone.module.like.dto.LikeResDto;
import com.elice.homealone.module.like.service.LikeService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 좋아요 등록
//    @PostMapping
//    public ResponseEntity<LikeResDto> createLike(@AuthenticationPrincipal Member member, @RequestBody LikeReqDto requestDto) {
//        LikeResDto resDto = likeService.createLike(requestDto, member);
//        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
//    }

    // 게시물 좋아요 조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<LikeResDto>> findLikeListByPostId(@PathVariable Long postId) {
        List<LikeResDto> resDtos = likeService.findLikeListByPostId(postId);
        return new ResponseEntity<>(resDtos, HttpStatus.OK);
    }

//    // 좋아요 취소
//    @DeleteMapping("/{likeId}")
//    public ResponseEntity<Void> deleteLike(@PathVariable Long likeId) {
//        likeService.deleteLike(likeId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    // 좋아요 등록 / 삭제
    @PostMapping
    public ResponseEntity<LikeResDto> createAndDeleteLike(@RequestBody LikeReqDto requestDto) {
        LikeResDto resDto = likeService.createAndDeleteLike(requestDto);
        if(resDto==null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }
}
