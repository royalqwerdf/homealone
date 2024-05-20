package com.elice.homealone.room.entity;


import com.elice.homealone.category.entity.Category;
import com.elice.homealone.common.BaseEntity;
import com.elice.homealone.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;
//
//    @Column(nullable = false)
//    @Lob
//    private String content;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "room" , fetch = FetchType.LAZY)
    private List<RoomContent> contents = new ArrayList<>();
    @Column(name = "view")
    private Integer view;

}
