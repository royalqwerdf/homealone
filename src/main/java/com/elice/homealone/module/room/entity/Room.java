package com.elice.homealone.module.room.entity;

import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.post.entity.Post;
import com.elice.homealone.module.room.dto.RoomRequestDTO;
import com.elice.homealone.module.tag.entity.PostTag;
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
@EqualsAndHashCode(callSuper = false)
public class Room extends Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(columnDefinition = "LONGTEXT")
    private String plainContent;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "view")
    @Builder.Default
    private Integer view = 0;

    @Builder.Default
    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<RoomImage> roomImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<RoomViewLog> roomViewLog = new ArrayList<>();

//    public static Room toRoom(RoomRequestDTO roomDto){
//        return Room.builder()
//                .title(roomDto.getTitle())
//                .content(roomDto.getContent())
//                .thumbnailUrl(roomDto.getThumbnailUrl())
//                .roomImages(roomDto.getImages())
//                .build();
//    }

    public Room(RoomRequestDTO roomDto, Member member) {
        super(member,Type.ROOM);
        this.view = 0;
        this.title = roomDto.getTitle();
        this.content = roomDto.getContent();
        this.thumbnailUrl = roomDto.getThumbnailUrl();
        this.roomImages = roomDto.getRoomImages().stream()
                .map(url -> new RoomImage(url, this))
                .collect(Collectors.toList());
    }
    public Room(Member member,String title, String content, String thumbnailUrl){
        super(member,Type.ROOM);
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
    }
    @Override
    public void addTag(PostTag tag) {
        super.addTag(tag);
    }
}
