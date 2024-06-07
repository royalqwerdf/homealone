package com.elice.homealone.room.entity;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.room.dto.RoomRequestDTO;
import com.elice.homealone.tag.entity.PostTag;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "content")
    @Lob
    @JsonSerialize(using = RawContentSerializer.class)
    private String content;

    @Column(name = "plain_content")
    @Lob
    private String plainContent;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;


    @Column(name = "view")
    @Builder.Default
    private Integer view = 0;


    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<RoomImage> roomImages = new ArrayList<>();


//    public static Room toRoom(RoomRequestDTO roomDto){
//        return Room.builder()
//                .title(roomDto.getTitle())
//                .content(roomDto.getContent())
//                .thumbnailUrl(roomDto.getThumbnailUrl())
//                .roomImages(roomDto.getImages())
//                .build();
//    }

    public Room(RoomRequestDTO roomDto,Member member) {
        super(member,Type.ROOM);
        this.view = 0;
        this.title = roomDto.getTitle();
        this.content = roomDto.getContent();
        this.thumbnailUrl = roomDto.getThumbnailUrl();
        this.roomImages = roomDto.getRoomImages().stream()
                .map(url -> new RoomImage(url, this))
                .collect(Collectors.toList());
    }

    @Override
    public void addTag(PostTag tag) {
        super.addTag(tag);
    }
}
