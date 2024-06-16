package com.elice.homealone.module.post.entity;

import com.elice.homealone.module.comment.entity.Comment;

import com.elice.homealone.global.common.BaseTimeEntity;
import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.like.entity.Like;
import com.elice.homealone.module.recipe.entity.RecipeDetail;
import com.elice.homealone.module.recipe.entity.RecipeImage;
import com.elice.homealone.module.recipe.entity.RecipeIngredient;
import com.elice.homealone.module.scrap.entity.Scrap;
import com.elice.homealone.module.tag.entity.PostTag;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostTag> tags = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post" , cascade = CascadeType.ALL)
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

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

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }


    protected void preRemovePost() {
        List<PostTag> tags = new ArrayList<>(this.getTags());
        List<Comment> comments = new ArrayList<>(this.getComments());
        List<Scrap> scraps = new ArrayList<>(this.getScraps());
        List<Like> likes = new ArrayList<>(this.getLikes());

        // 별도의 컬렉션에 저장된 엔티티를 원래의 컬렉션에서 삭제
        this.getTags().removeAll(tags);
        this.getComments().removeAll(comments);
        this.getScraps().removeAll(scraps);
        this.getLikes().removeAll(likes);
    }
}

