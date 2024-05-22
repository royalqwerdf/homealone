package com.elice.homealone.scrap.entity;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @Builder
    public Scrap(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}
