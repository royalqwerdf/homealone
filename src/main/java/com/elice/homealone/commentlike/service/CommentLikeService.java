package com.elice.homealone.commentlike.service;

import com.elice.homealone.commentlike.Repository.CommentLikeRepository;
import com.elice.homealone.commentlike.dto.CommentLikeReqDto;
import com.elice.homealone.commentlike.dto.CommentLikeResDto;
import com.elice.homealone.commentlike.entity.CommentLike;
import com.elice.homealone.comment.entity.Comment;
import com.elice.homealone.comment.repository.CommentRepository;
import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.AuthService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentLikeService {

        private final CommentLikeRepository commentLikeRepository;
        private final CommentRepository commentRepository;

        private final AuthService authService;

        @Transactional
        public CommentLikeResDto createCommentLike(CommentLikeReqDto reqDto) {
                Member member = authService.getMember();
                Comment comment = commentRepository.findById(reqDto.getCommentId()).orElseThrow(()-> new HomealoneException(ErrorCode.COMMENT_NOT_FOUND));
                CommentLike commentLike = reqDto.toEntity(member, comment);
                commentLikeRepository.save(commentLike);

                return CommentLikeResDto.fromEntity(commentLike);
        }

        @Transactional
        public void deleteCommentLike(Long commentLikeId) {
                CommentLike like = commentLikeRepository.findById(commentLikeId)
                    .orElseThrow(() -> new HomealoneException(ErrorCode.COMMENT_NOT_FOUND));
                commentLikeRepository.delete(like);
        }

        // 멤버가 좋아요 한 리스트 조회
        public List<CommentLike> findLikesByMemberAndCommentIn(Member member, List<Comment> comments) {
                return commentLikeRepository.findByMemberAndCommentIn(member, comments);
        }


}
