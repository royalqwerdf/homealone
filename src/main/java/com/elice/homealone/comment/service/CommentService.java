package com.elice.homealone.comment.service;

import com.elice.homealone.commentlike.entity.CommentLike;
import com.elice.homealone.commentlike.service.CommentLikeService;
import com.elice.homealone.comment.dto.CommentReqDto;
import com.elice.homealone.comment.dto.CommentResDto;
import com.elice.homealone.comment.entity.Comment;
import com.elice.homealone.comment.repository.CommentRepository;
import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.AuthService;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.post.sevice.PostService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    @Lazy
    private final CommentLikeService commentLikeService;
    private final PostService postService;
    private final MemberService memberService;
    private final AuthService authService;

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
        try {
            Member member = authService.getMember();
            List<CommentLike> likes = commentLikeService.findLikesByMemberAndCommentIn(member, comments);
            Set<Long> likedCommentIds = likes.stream()
                .map(commentLike -> commentLike.getComment().getId())
                .collect(Collectors.toSet());

            return comments.stream()
                .map(comment -> {
                    CommentResDto resDto = CommentResDto.fromEntity(comment);
                    resDto.setLikeByCurrentUser(likedCommentIds.contains(comment.getId()));
                    return resDto;
                })
                .collect(Collectors.toList());
        } catch (HomealoneException e) {
            if (e.getErrorCode()==ErrorCode.MEMBER_NOT_FOUND) {
                return comments.stream()
                    .map(CommentResDto::fromEntity)
                    .collect(Collectors.toList());
            } else {
                throw new HomealoneException(ErrorCode.COMMENT_NOT_FOUND);
            }
        }
    }

    // 댓글 수정
    @Transactional
    public CommentResDto updateComment(Member member, CommentReqDto requestDto) {
        Comment comment = commentRepository.findById(requestDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("Comment not found with id"));
        comment.setContent(requestDto.getContent());
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

    public Comment findById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new HomealoneException(
            ErrorCode.COMMENT_NOT_FOUND));
        return comment;
    }

    // 로그인 멤버 댓글 조회
    @Transactional
    public Page<CommentResDto> findCommentByMember(Pageable pageable) {
        Member member = authService.getMember();
        Page<Comment> comments = commentRepository.findByMemberIdOrderByPostIdDescCreatedAtDesc(member.getId(), pageable);
        Page<CommentResDto> resDtos = comments.map(CommentResDto::fromEntity);
        return resDtos;
    }
}
