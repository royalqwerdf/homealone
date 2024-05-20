package com.elice.homealone.like.entity;


import com.elice.homealone.category.entity.Category;
import com.elice.homealone.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
//좋아요 엔티티
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="content_id")
    private Long contentId;

    @Column(name="content_title")
    private String title;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;




}
