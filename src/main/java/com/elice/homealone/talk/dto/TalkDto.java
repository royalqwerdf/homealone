package com.elice.homealone.talk.dto;

import com.elice.homealone.room.dto.RoomDto;
import com.elice.homealone.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TalkDto {
    private String title;
    private String content;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TalkInfoDto extends TalkDto {
        private long id;
        private Date createdAt;
        private Date updatedAt;
        private Integer view;
        private Long likeCount;
        private Long scrapCount;
        private String memberName;
        private Integer commentCount;

        public static TalkInfoDto toRoomInfoDto(Room room) {
            return TalkInfoDto.builder()
                    .id(room.getId())
                    .title(room.getTitle())
                    .content(room.getContent())
                    .createdAt(room.getCreatedAt())
                    .updatedAt(room.getUpdatedAt())
                    .view(room.getView())
                    .likeCount((long) room.getPostLikes().size())
                    .scrapCount((long) room.getScraps().size())
                    .memberName(room.getMember().getName())
                    .commentCount(room.getComments().size())
                    .build();
        }
    }
}
