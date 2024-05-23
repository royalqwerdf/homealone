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
@Builder
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
                .title(talkDto.getTitle())
                .content(talkDto.getContent())
                .build();
    }


    public static Talk createTalk(TalkDto talkDto) {
        Talk talk = new Talk();
        talk.title = talkDto.getContent();
        talk.content = talkDto.getContent();
        talk.setType(Type.TALK); // Post의 type 필드를 설정
        return talk;
    }
}
