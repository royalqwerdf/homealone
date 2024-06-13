package com.elice.homealone.module.talk.entity;

import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.post.entity.Post;
import com.elice.homealone.module.tag.entity.PostTag;
import com.elice.homealone.module.talk.dto.TalkRequestDTO;
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


    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(columnDefinition = "LONGTEXT")
    private String plainContent;

    @OneToMany(mappedBy = "talk", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TalkImage> talkImages;

    @Column(name = "view")
    private Integer view;

    @OneToMany(mappedBy = "talk", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TalkViewLog> talkViewLogs= new ArrayList<>();
    public static Talk toTalk(TalkRequestDTO talkDto){
        return Talk.builder()
                .title(talkDto.getTitle())
                .content(talkDto.getContent())
                .build();
    }


    public Talk(TalkRequestDTO talkDto, Member member) {
        super(member,Type.TALK);
        this.title = talkDto.getTitle();
        this.content = talkDto.getContent();
        this.view = 0;
    }

    @Override
    public void addTag(PostTag tag) {
        super.addTag(tag);
    }
}
