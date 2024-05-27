package com.elice.homealone.talk.dto;

import com.elice.homealone.post.entity.Post;
import com.elice.homealone.room.dto.RoomDto;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.talk.entity.Talk;
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
public class TalkDto {
    private String title;
    private String content;
    private Post.Type type;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TalkInfoDto extends TalkDto {
        private long id;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Integer view;
        private Integer likeCount;
        private Integer scrapCount;
        private String memberName;
        private Integer commentCount;

        public static TalkInfoDto toTalkInfoDto(Talk talk) {
            return TalkInfoDto.builder()
                    .id(talk.getId())
                    .type(talk.getType())
                    .title(talk.getTitle())
                    .content(talk.getContent())
                    .createdAt(talk.getCreatedAt())
                    .modifiedAt(talk.getModifiedAt())
                    .view(talk.getView())
                    .likeCount(talk.getPostLikes().size())
                    .scrapCount( talk.getScraps().size())
                    .memberName(talk.getMember().getName())
                    .commentCount(talk.getComments().size())
                    .build();
        }
    }
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TalkInfoDtoForMember extends TalkInfoDto {
      private Boolean like;
      private Boolean scrap;

        public static TalkInfoDtoForMember toTalkInfoDtoForMember(Talk talk) {
            return TalkInfoDtoForMember.builder()
                    .id(talk.getId())
                    .type(talk.getType())
                    .title(talk.getTitle())
                    .content(talk.getContent())
                    .createdAt(talk.getCreatedAt())
                    .modifiedAt(talk.getModifiedAt())
                    .view(talk.getView())
                    .likeCount(talk.getPostLikes().size())
                    .scrapCount( talk.getScraps().size())
                    .memberName(talk.getMember().getName())
                    .commentCount(talk.getComments().size())
                    .build();
        }
    }
}
