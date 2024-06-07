package com.elice.homealone.talk.entity;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.room.entity.RawContentSerializer;
import com.elice.homealone.tag.entity.PostTag;
import com.elice.homealone.talk.dto.TalkRequestDTO;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonSerialize(using = RawContentSerializer.class)
    private String content;

    @Column(name = "plain_content")
    private String plainContent;

    @OneToMany(mappedBy = "talk", fetch = FetchType.LAZY)
    private List<TalkImage> talkImages;

    @Column(name = "view")
    private Integer view;

    public static Talk toTalk(TalkRequestDTO talkDto){
        return Talk.builder()
                .title(talkDto.getTitle())
                .content(talkDto.getContent())
                .build();
    }


    public Talk(TalkRequestDTO talkDto, Member member) {
        super(member,Type.TALK);
        this.title = talkDto.getContent();
        this.content = talkDto.getContent();
        this.view = 0;
    }

    @Override
    public void addTag(PostTag tag) {
        super.addTag(tag);
    }
}
