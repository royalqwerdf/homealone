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
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.post.sevice.PostService;
import com.elice.homealone.scrap.entity.Scrap;
import com.elice.homealone.scrap.repository.ScrapRepository;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.CommonQueryContract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentLikeService {

        private final CommentLikeRepository commentLikeRepository;
        private final CommentRepository commentRepository;
        private final MemberService memberService;

        private final AuthService authService;

        @Transactional
        public CommentLikeResDto createAndDeleteCommentLike(CommentLikeReqDto reqDto) {
                try {
                        Member member = authService.getMember();
                        member = memberService.findById(member.getId());
                        Comment comment = commentRepository.findById(reqDto.getCommentId()).get();
                        if(member==null) {
                                return null;
                        }
                        Optional<CommentLike> commentLike = commentLikeRepository.findByMemberIdAndCommentId(member.getId(), comment.getId());
                        if(commentLike.isEmpty()) {
                                CommentLike newCommentLike = reqDto.toEntity(member, comment);
                                member.getCommentLikes().add(newCommentLike);
                                comment.getLikes().add(newCommentLike);
                                commentLikeRepository.save(newCommentLike);
                                CommentLikeResDto resDto = CommentLikeResDto.fromEntity(newCommentLike);
                                resDto.setTotalCount(comment.getLikes().size());
                                return resDto;
                        }
                        commentLikeRepository.delete(commentLike.get());
                        return null;
                } catch (HomealoneException e) {
                        if (e.getErrorCode()== ErrorCode.MEMBER_NOT_FOUND) {
                                return null;
                        } else {
                                throw new HomealoneException(ErrorCode.BAD_REQUEST);
                        }
                }
        }

        // 멤버가 좋아요 한 리스트 조회
        public List<CommentLike> findLikesByMemberAndCommentIn(Member member, List<Comment> comments) {
                return commentLikeRepository.findByMemberAndCommentIn(member, comments);
        }


}
