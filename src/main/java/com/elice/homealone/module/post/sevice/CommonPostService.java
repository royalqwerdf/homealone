package com.elice.homealone.module.post.sevice;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.module.post.entity.Post;
import com.elice.homealone.module.post.repository.PostRepository;
import com.elice.homealone.module.recipe.entity.Recipe;
import com.elice.homealone.module.room.entity.Room;
import com.elice.homealone.module.talk.entity.Talk;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonPostService {

    private final PostRepository postRepository;

    public String getTitle(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new HomealoneException(
            ErrorCode.POST_NOT_FOUND));

        if (post instanceof Recipe recipe) {
            return recipe.getTitle();
        } else if (post instanceof Talk talk) {
            // Talk 클래스에 getTitle() 메소드가 있다고 가정
            return talk.getTitle();
        } else if (post instanceof Room room) {
            // Room 클래스에 getTitle() 메소드가 있다고 가정
            return room.getTitle();
        } else {
            throw new IllegalArgumentException("Post is not of the expected type");
        }
    }
}
