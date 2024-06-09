package com.elice.homealone.comment.controller;

import com.elice.homealone.comment.dto.CommentReqDto;
import com.elice.homealone.comment.dto.CommentResDto;
import com.elice.homealone.comment.service.CommentService;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.recipe.dto.RecipeRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping
    public ResponseEntity<CommentResDto> createComment(@AuthenticationPrincipal Member member, @RequestBody
        CommentReqDto requestDto) {
        CommentResDto resDto = commentService.createComment(requestDto, member);
        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }

    // 게시물 댓글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResDto>> findCommentListByPostId(@PathVariable Long postId) {
        List<CommentResDto> resDtos = commentService.findCommentListByPostId(postId);
        return new ResponseEntity<>(resDtos, HttpStatus.OK);
    }

    // 댓글 수정
    @PatchMapping
    public ResponseEntity<CommentResDto> updateComment(@AuthenticationPrincipal Member member, @RequestBody CommentReqDto requestDto) {
        CommentResDto resDto = commentService.updateComment(member, requestDto);
        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
