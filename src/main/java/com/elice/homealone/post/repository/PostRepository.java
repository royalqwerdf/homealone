package com.elice.homealone.post.repository;

import com.elice.homealone.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteById(Long id);

}
