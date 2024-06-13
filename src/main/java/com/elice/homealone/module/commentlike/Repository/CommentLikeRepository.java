package com.elice.homealone.module.commentlike.Repository;

import com.elice.homealone.module.commentlike.entity.CommentLike;
import com.elice.homealone.module.comment.entity.Comment;
import com.elice.homealone.module.member.entity.Member;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentAndMember(Comment comment, Member member);
    List<CommentLike> findByMemberAndCommentIn(Member member, List<Comment> comments);

    Optional<CommentLike> findByMemberIdAndCommentId(Long memberId, Long commentId);
}
