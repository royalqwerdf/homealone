package com.elice.homealone.post.entity;

import com.elice.homealone.comment.entity.Comment;
import com.elice.homealone.common.BaseEntity;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.postlike.entity.PostLike;
import com.elice.homealone.scrap.entity.Scrap;
import com.elice.homealone.tag.entity.PostTagMap;
import jakarta.persistence.*;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<PostTagMap> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<PostLike> postLikes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Scrap> scraps;

    public enum Type{
        RECIPE,
        ROOM,
        TALK
    }
}