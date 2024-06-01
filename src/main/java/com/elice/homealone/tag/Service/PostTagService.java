package com.elice.homealone.tag.Service;

import com.elice.homealone.tag.Repository.PostTagRepository;
import com.elice.homealone.tag.dto.PostTagDto;
import com.elice.homealone.tag.entity.PostTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostTagService {

    private final PostTagRepository postTagRepository;
    private final TagService tagService;

    public PostTag createPostTag(PostTagDto postTagDto) {
        PostTag postTag = PostTag.builder()
            .name(postTagDto.getTagName())
            .build();

        postTagRepository.save(postTag);
        tagService.addTag(postTag);
        return postTag;
    }
}
