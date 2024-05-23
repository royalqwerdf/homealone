package com.elice.homealone.room.entity;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.room.dto.RoomDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(nullable = false)
    @Lob
    private String content;


    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "view")
    private Integer view;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY)
    private List<RoomImage> roomImages;

    public static Room toRoom(RoomDto roomDto){
        return Room.builder()
                .title(roomDto.getTitle())
                .content(roomDto.getContent())
                .thumbnailUrl(roomDto.getThumbnailUrl())
                .build();
    }

    public static Room createRoom(RoomDto roomDto) {
        Room room = new Room();
        room.title = roomDto.getTitle();
        room.content = roomDto.getContent();
        room.thumbnailUrl = room.getThumbnailUrl();
        room.setType(Type.ROOM); // Post의 type 필드를 설정
        return room;
    }
}
