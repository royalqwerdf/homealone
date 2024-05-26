package com.elice.homealone.room.entity;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.room.dto.RoomDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import lombok.experimental.SuperBuilder;

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
    private String content;

    @Column(name = "plain_content")
    private String plainContent;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;


    @Column(name = "view")
    @Builder.Default
    private Integer view = 0;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY)
    private List<RoomImage> roomImages;

    public static Room toRoom(RoomDto roomDto){
        return Room.builder()
                .title(roomDto.getTitle())
                .content(roomDto.getContent())
                .thumbnailUrl(roomDto.getThumbnailUrl())
                .build();
    }

//    public Room(RoomDto roomDto,Member member) {
//        super(member,Type.ROOM);
//        this.title = roomDto.getTitle();
//        this.content = roomDto.getContent();
//        this.thumbnailUrl = roomDto.getThumbnailUrl();
//    }

    public Room(RoomDto roomDto) {
        this.title = roomDto.getTitle();
        this.content = roomDto.getContent();
        this.thumbnailUrl = roomDto.getThumbnailUrl();
    }
}
