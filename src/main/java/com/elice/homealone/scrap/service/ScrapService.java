package com.elice.homealone.scrap.service;

import com.elice.homealone.scrap.dto.ScrapReqDto;
import com.elice.homealone.scrap.dto.ScrapResDto;
import com.elice.homealone.scrap.entity.Scrap;
import com.elice.homealone.scrap.repository.ScrapRepository;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.post.sevice.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final PostService postService;

    // 북마크 등록
    @Transactional
    public ScrapResDto createScrap(ScrapReqDto reqDto, Member member) {
        Post post = postService.findById(reqDto.getPostId());


        if (!scrapRepository.existsByPostIdAndMemberId(post.getId(), member.getId())) {
            Scrap scrap = Scrap.builder()
                    .post(post)
                    .member(member)
                    .build();
            scrapRepository.save(scrap);
            return ScrapResDto.fromEntity(scrap);
        } else {
            throw new IllegalStateException("이미 북마크 되어있는 게시물입니다.");
        }
    }

    // 게시물 북마크 개수 조회
    public long countScrapsByPostId(Long postId) {
        return scrapRepository.countByPostId(postId);
    }

    // 북마크 취소
    @Transactional
    public void deleteScrap(Long scrapId) {
        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new IllegalArgumentException("Scrap not found with id: " + scrapId));
        scrapRepository.delete(scrap);
    }
}
