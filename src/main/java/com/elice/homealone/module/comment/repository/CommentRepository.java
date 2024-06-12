package com.elice.homealone.module.comment.repository;

import com.elice.homealone.module.comment.entity.Comment;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findByPostId(Long postId);

    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

    Page<Comment> findByMemberIdOrderByPostIdDescCreatedAtDesc(Long memberId, Pageable pageable);
}
