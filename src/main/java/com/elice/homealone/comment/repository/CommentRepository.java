package com.elice.homealone.comment.repository;

import com.elice.homealone.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findByPostId(Long postId);

    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
}
