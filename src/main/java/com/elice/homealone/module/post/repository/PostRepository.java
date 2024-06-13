package com.elice.homealone.module.post.repository;

import com.elice.homealone.module.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    int countById(Long id);
    Optional<Post> findById(Long id);

}
