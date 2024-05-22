package com.elice.homealone.talk.entity;

import com.elice.homealone.common.BaseEntity;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Talk extends Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "title")
    private String title;

//    @Column(nullable = false)
//    @Lob
//    private String content;

    @OneToMany(mappedBy = "talk", fetch = FetchType.LAZY)
    private List<TalkContent> talkContents = new ArrayList<>();

    @Column(name = "view")
    private Integer view;
}
