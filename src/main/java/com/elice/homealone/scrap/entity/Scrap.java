package com.elice.homealone.scrap.entity;


import com.elice.homealone.category.entity.Category;
import com.elice.homealone.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
