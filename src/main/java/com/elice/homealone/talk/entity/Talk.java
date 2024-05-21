package com.elice.homealone.talk.entity;

import com.elice.homealone.category.entity.Category;
import com.elice.homealone.common.BaseEntity;
import com.elice.homealone.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Talk extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "title")
    private String title;

//    @Column(nullable = false)
//    @Lob
//    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY)
    private List<TalkContent> talkContents = new ArrayList<>();
    @Column(name = "view")
    private Integer view;

}
