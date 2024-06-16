package com.elice.homealone.module.tag.Repository;

import com.elice.homealone.module.post.entity.Post;
import com.elice.homealone.module.recipe.entity.Recipe;
import com.elice.homealone.module.tag.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    void deleteByPost(Post post);
}
