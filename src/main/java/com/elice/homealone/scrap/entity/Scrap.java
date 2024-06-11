package com.elice.homealone.scrap.entity;

import com.elice.homealone.global.common.BaseTimeEntity;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.scrap.dto.ScrapReqDto;
import com.elice.homealone.scrap.repository.ScrapRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "scraps")
@Getter
public class Scrap extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @Setter
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Setter
    private Member member;

    @Builder
    public Scrap(Post post, Member member) {
        this.post = post;
        this.member = member;
    }

    @PreRemove
    public void preRemove() {
        this.post.getScraps().remove(this);
        this.member.getScraps().remove(this);
    }
}
