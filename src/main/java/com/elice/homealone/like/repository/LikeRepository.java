package com.elice.homealone.like.repository;

import com.elice.homealone.like.entity.Like;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom {
    List<Like> findByPostId(Long postId);
    List<Like> findByPostIdOrderByCreatedAtDesc(Long postId);

    boolean existsByPostAndMember(Post post, Member member);
    List<Like> findByMemberAndPostIn(Member member, List<Post> posts);
}
