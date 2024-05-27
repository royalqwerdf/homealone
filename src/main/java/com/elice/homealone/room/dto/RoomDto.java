package com.elice.homealone.room.dto;

import com.elice.homealone.comment.entity.Comment;
import com.elice.homealone.global.exception.ErrorMessage;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.room.entity.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.List;


import java.time.LocalDateTime;



@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    @NotBlank(message = ErrorMessage.TITLE_REQUIRED)
    private String title;
    @NotBlank(message = ErrorMessage.CONTENT_REQUIRED)
    private String content;
    private Post.Type type;
    @NotBlank(message = ErrorMessage.THUMBNAIL_URL_REQUIRED)
    private String thumbnailUrl;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfoDto extends RoomDto {
        private long id;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer view;
        private Integer likeCount;
        private Integer scrapCount;
        private String memberName;
        private Integer commentCount;
        private List<Comment> comments;

        public static RoomInfoDto toRoomInfoDto(Room room) {
            return RoomInfoDto.builder()
                    .id(room.getId())
                    .title(room.getTitle())
                    .thumbnailUrl(room.getThumbnailUrl())
                    .type(room.getType())
                    .content(room.getContent())
                    .createdAt(room.getCreatedAt())
                    .updatedAt(room.getModifiedAt())
                    .view(room.getView())
                    .likeCount( room.getPostLikes() != null ? room.getPostLikes().size() : 0)
                    .scrapCount(room.getScraps() != null ? room.getScraps().size() : 0)
                    .memberName(room.getMember().getName())
                    .commentCount(room.getComments() != null ? room.getComments().size() : 0)
                    .build();
        }
    }
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfoDtoForMember extends RoomInfoDto {
        private Boolean scrap;
        private Boolean like;
        public static RoomInfoDtoForMember toRoomInfoDtoForMember(Room room) {
            return RoomInfoDtoForMember.builder()
                    .id(room.getId())
                    .title(room.getTitle())
                    .thumbnailUrl(room.getThumbnailUrl())
                    .type(room.getType())
                    .content(room.getContent())
                    .createdAt(room.getCreatedAt())
                    .updatedAt(room.getModifiedAt())
                    .view(room.getView())
                    .likeCount( room.getPostLikes() != null ? room.getPostLikes().size() : 0)
                    .scrapCount(room.getScraps() != null ? room.getScraps().size() : 0)
                    .memberName(room.getMember().getName())
                    .commentCount(room.getComments() != null ? room.getComments().size() : 0)
                    .build();
        }
    }

}
