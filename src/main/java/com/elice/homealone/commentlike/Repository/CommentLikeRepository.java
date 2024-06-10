package com.elice.homealone.commentlike.Repository;

import com.elice.homealone.commentlike.entity.CommentLike;
import com.elice.homealone.comment.entity.Comment;
import com.elice.homealone.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentAndMember(Comment comment, Member member);
    List<CommentLike> findByMemberAndCommentIn(Member member, List<Comment> comments);
}
