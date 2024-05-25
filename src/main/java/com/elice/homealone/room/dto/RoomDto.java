package com.elice.homealone.room.dto;

import com.elice.homealone.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private String title;
    private String content;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfoDto extends RoomDto {
        private long id;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer view;
        private Long likeCount;
        private Long scrapCount;
        private String memberName;
        private Integer commentCount;

        public static RoomInfoDto toRoomInfoDto(Room room) {
            return RoomInfoDto.builder()
                    .id(room.getId())
                    .title(room.getTitle())
                    .content(room.getContent())
                    .createdAt(room.getCreatedAt())
                    .updatedAt(room.getModifiedAt())
                    .view(room.getView())
                    .likeCount((long) room.getPostLikes().size())
                    .scrapCount((long) room.getScraps().size())
                    .memberName(room.getMember().getName())
                    .commentCount(room.getComments().size())
                    .build();
        }
    }
}
