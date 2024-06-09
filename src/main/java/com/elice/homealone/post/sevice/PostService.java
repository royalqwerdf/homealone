package com.elice.homealone.post.sevice;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    public Post findById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new HomealoneException(ErrorCode.POST_NOT_FOUND));
        return post;
    }
}
