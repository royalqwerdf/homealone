package com.elice.homealone.room.dto;

import com.elice.homealone.comment.entity.Comment;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.room.entity.Room;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.List;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    @NotNull(message = "제목을 입력해주세요.")
    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;
    @NotNull(message = "내용을 입력해주세요.")
    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;
    private Post.Type type;
    @NotNull(message = "대표이미지를 등록해주세요.")
    @NotEmpty(message = "대표이미지를 등록해주세요.")
    private String thumbnailUrl;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfoDto extends RoomDto {
        private long id;
        private Date createdAt;
        private Date updatedAt;
        private Integer view;
        private Long likeCount;
        private Long scrapCount;
        private String memberName;
        private Integer commentCount;
        private List<Comment> comments;

        public static RoomInfoDto toRoomInfoDto(Room room) {
            return RoomInfoDto.builder()
                    .id(room.getId())
                    .title(room.getTitle())
                    .type(room.getType())
                    .content(room.getContent())
                    .createdAt(room.getCreatedAt())
                    .updatedAt(room.getUpdatedAt())
                    .view(room.getView())
                    .likeCount((long) room.getPostLikes().size())
                    .scrapCount((long) room.getScraps().size())
                    .memberName(room.getMember().getName())
                    .commentCount(room.getComments().size())
                    .comments(room.getComments())
                    .build();
        }
    }
}
