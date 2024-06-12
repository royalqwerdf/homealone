package com.elice.homealone.module.commentlike.controller;

import com.elice.homealone.module.commentlike.dto.CommentLikeReqDto;
import com.elice.homealone.module.commentlike.dto.CommentLikeResDto;
import com.elice.homealone.module.commentlike.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commentLikes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public ResponseEntity<CommentLikeResDto> createAndDeleteCommentLike(@RequestBody CommentLikeReqDto reqDto) {
        CommentLikeResDto resDto = commentLikeService.createAndDeleteCommentLike(reqDto);
        if(resDto==null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }
}
