package com.elice.homealone.comment.entity;


import com.elice.homealone.category.entity.Category;
import com.elice.homealone.common.BaseEntity;
import com.elice.homealone.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="content")
    private String content;

    @Column(name="content_id")
    private Long contentId;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;


}
