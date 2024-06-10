package com.elice.homealone.like.service;

import com.elice.homealone.like.dto.LikeReqDto;
import com.elice.homealone.like.dto.LikeResDto;
import com.elice.homealone.like.entity.Like;
import com.elice.homealone.like.repository.LikeRepository;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.post.sevice.PostService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberService memberService;

    private final PostService postService;

    // 좋아요 등록.
    @Transactional
    public LikeResDto createLike(LikeReqDto reqDto, Member member) {
        Post post = postService.findById(reqDto.getPostId());
        Like like = reqDto.toEntity(member, post);
        likeRepository.save(like);

        return LikeResDto.fromEntity(like);
    }

    // 게시물 좋아요 list 조회
    public List<LikeResDto> findLikeListByPostId(Long postId) {
        List<Like> likes = likeRepository.findByPostIdOrderByCreatedAtDesc(postId);
        return likes.stream()
                .map(LikeResDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 좋아요 취소
    @Transactional
    public void deleteLike(Long likeId) {
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new IllegalArgumentException("Like not found with id: " + likeId));
        likeRepository.delete(like);
    }

    // 멤버가 게시글을 좋아요 했는지 여부
    public boolean isLikedByMember(Post post, Member member) {
        return likeRepository.existsByPostAndMember(post, member);
    }

    // 멤버가 좋아요 한 리스트 조회
    public List<Like> findLikesByMemberAndPostIn(Member member, List<Post> posts) {
        return likeRepository.findByMemberAndPostIn(member, posts);
    }
}
