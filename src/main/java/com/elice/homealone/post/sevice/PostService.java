package com.elice.homealone.post.sevice;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.post.dto.PostResPageDto;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.post.repository.PostRepository;

import com.elice.homealone.recipe.entity.Recipe;
import com.elice.homealone.recipe.service.RecipeService;
import com.elice.homealone.room.service.RoomService;
import com.elice.homealone.talk.Service.TalkService;
import java.awt.print.Pageable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    private final RecipeService recipeService;
    private final RoomService roomService;
    private final TalkService talkService;

    public Post findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new HomealoneException(ErrorCode.POST_NOT_FOUND));
        return post;
    }
}
