package com.elice.homealone.module.post.repository;

import com.elice.homealone.module.post.entity.Post;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    int countById(Long id);
    Optional<Post> findById(Long id);

    @Query("SELECT p FROM Post p " +
        "LEFT JOIN p.likes l " +
        "WHERE p.type = :type AND p.createdAt >= :oneWeekAgo " +
        "GROUP BY p.id " +
        "ORDER BY COUNT(l) DESC, p.createdAt DESC")
    Page<Post> findRecipesByLikesInLastWeek(@Param("type") Post.Type type, @Param("oneWeekAgo") LocalDateTime oneWeekAgo, Pageable pageable);
}
