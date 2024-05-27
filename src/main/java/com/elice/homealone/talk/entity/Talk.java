package com.elice.homealone.talk.entity;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.talk.dto.TalkDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import lombok.experimental.SuperBuilder;

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


    public Talk(TalkDto talkDto, Member member) {
        super(member,Type.TALK);
        this.title = talkDto.getContent();
        this.content = talkDto.getContent();
        this.view = 0;
    }
}
