package com.elice.homealone.post.entity;

import com.elice.homealone.comment.entity.Comment;

import com.elice.homealone.global.common.BaseTimeEntity;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.postlike.entity.PostLike;
import com.elice.homealone.scrap.entity.Scrap;
import com.elice.homealone.tag.entity.PostTag;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {
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
    private List<PostTag> tags = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Scrap> scraps = new ArrayList<>();

    public enum Type{
        RECIPE,
        ROOM,
        TALK,
        USEDTRADE
    }

    //@Builder
    protected Post(Member member, Type type) {
        this.member = member;
        this.type = type;
    }

    public void addTag(PostTag tag) {
        this.tags.add(tag);
        tag.setPost(this);
    }
}

