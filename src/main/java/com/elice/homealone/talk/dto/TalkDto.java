package com.elice.homealone.talk.dto;

import com.elice.homealone.room.dto.RoomDto;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.talk.entity.Talk;
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

        public static TalkInfoDto toRoomInfoDto(Talk talk) {
            return TalkInfoDto.builder()
                    .id(talk.getId())
                    .title(talk.getTitle())
                    .content(talk.getContent())
                    .createdAt(talk.getCreatedAt())
                    .updatedAt(talk.getUpdatedAt())
                    .view(talk.getView())
                    .likeCount((long) talk.getPostLikes().size())
                    .scrapCount((long) talk.getScraps().size())
                    .memberName(talk.getMember().getName())
                    .commentCount(talk.getComments().size())
                    .build();
        }
    }
}
