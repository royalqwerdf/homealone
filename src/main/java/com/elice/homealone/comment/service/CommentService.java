package com.elice.homealone.comment.service;

import com.elice.homealone.comment.dto.CommentReqDto;
import com.elice.homealone.comment.dto.CommentResDto;
import com.elice.homealone.comment.entity.Comment;
import com.elice.homealone.comment.repository.CommentRepository;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.post.sevice.PostService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberService memberService;

    // 댓글 등록
    @Transactional
    public CommentResDto createComment(CommentReqDto reqDto, Member member) {
        Post post = postService.findById(reqDto.getPostId());
        Comment comment = reqDto.toEntity(member,post);
        commentRepository.save(comment);

        return CommentResDto.fromEntity(comment);
    }

    // 게시물 댓글 리스트 조회
    public List<CommentResDto> findCommentListByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
        return comments.stream()
            .map(CommentResDto::fromEntity)
            .collect(Collectors.toList());
    }

    // 댓글 수정
    @Transactional
    public CommentResDto updateComment(Long commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));
        comment.setContent(newContent);
        commentRepository.save(comment);
        return CommentResDto.fromEntity(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }
}
