package com.elice.homealone.talk.entity;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.talk.dto.TalkDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Talk extends Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "title")
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "talk", fetch = FetchType.LAZY)
    private List<TalkImage> talkImages;

    @Column(name = "view")
    private Integer view;

    public static Talk toTalk(TalkDto talkDto){
        return Talk.builder()
                .type(talkDto.getType())
                .title(talkDto.getTitle())
                .content(talkDto.getContent())
                .build();
    }

}
