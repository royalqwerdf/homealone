package com.elice.homealone.commentlike.controller;

import com.elice.homealone.commentlike.dto.CommentLikeReqDto;
import com.elice.homealone.commentlike.dto.CommentLikeResDto;
import com.elice.homealone.commentlike.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<CommentLikeResDto> createCommentLike(@RequestBody CommentLikeReqDto reqDto) {
        CommentLikeResDto resDto = commentLikeService.createCommentLike(reqDto);
        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentLikeId}")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable Long commentLikeId) {
        commentLikeService.deleteCommentLike(commentLikeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
