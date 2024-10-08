package com.elice.homealone.module.tag.Service;

import com.elice.homealone.module.post.entity.Post;
import com.elice.homealone.module.recipe.entity.Recipe;
import com.elice.homealone.module.tag.Repository.PostTagRepository;
import com.elice.homealone.module.tag.dto.PostTagDto;
import com.elice.homealone.module.tag.entity.PostTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostTagService {

    private final PostTagRepository postTagRepository;
    private final TagService tagService;

    @Transactional
    public PostTag createPostTag(PostTagDto postTagDto) {
        PostTag postTag = PostTag.builder()
            .name(postTagDto.getTagName())
            .build();

        postTagRepository.save(postTag);
        tagService.addTag(postTag);
        return postTag;
    }

    public void deletePostTagByRecipe(Post post) {
        postTagRepository.deleteByPost(post);
    }
}
