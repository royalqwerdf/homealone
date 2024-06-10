package com.elice.homealone.scrap.repository;

import com.elice.homealone.like.entity.Like;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.scrap.entity.Scrap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    long countByPostId(Long postId);
    boolean existsByPostIdAndMemberId(Long postId, Long memberId);
    void deleteByPostIdAndMemberId(Long postId, Long memberId);

    Optional<Scrap> findByMemberIdAndPostId(Long memberId, Long postId);
    List<Scrap> findByMemberAndPostIn(Member member, List<Post> posts);

    boolean existsByPostAndMember(Post post, Member member);
}
