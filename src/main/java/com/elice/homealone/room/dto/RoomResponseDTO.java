package com.elice.homealone.room.dto;

import com.elice.homealone.comment.entity.Comment;
import com.elice.homealone.like.service.LikeService;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.room.entity.RoomImage;
import com.elice.homealone.tag.dto.PostTagDto;
import com.elice.homealone.tag.entity.PostTag;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoomResponseDTO {

    private Long id;
    private String title;
    private String thumbnailUrl;
    private String memberName;
    private Integer commentCount;
    private LocalDateTime createdAt;
    private String contentSummary;
    @Builder.Default
    private Integer likeCount = 0;

    public static RoomResponseDTO toRoomResponseDTO(Room room){
        return RoomResponseDTO.builder()
                .id(room.getId())
                .title(room.getTitle())
                .thumbnailUrl(room.getThumbnailUrl())
                .memberName(room.getMember().getName())
                .commentCount(room.getComments().size())
                .createdAt(room.getCreatedAt())
                .contentSummary(room.getPlainContent().length() <=80 ? room.getPlainContent() : room.getPlainContent().substring(0,80))
                .likeCount( room.getLikes() != null ? room.getLikes().size() : 0)
                .build();
    }
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfoDto extends RoomResponseDTO {
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<PostTagDto> tags;
        @Builder.Default
        private List<String> roomImages = new ArrayList<>();
        private Integer view;
        private String memberName;
        private Integer commentCount;
        private Integer scrapCount;
        private Integer likeCount;
        private Boolean scrap;
        private Boolean like;

        public static RoomInfoDto toRoomInfoDto(Room room) {
            return RoomInfoDto.builder()
                    .id(room.getId())
                    .title(room.getTitle())
                    .thumbnailUrl(room.getThumbnailUrl())
                    .tags(room.getTags().stream().map(postTag -> postTag.toDto()).collect(Collectors.toList()))
                    .content(room.getContent())
                    .createdAt(room.getCreatedAt())
                    .updatedAt(room.getModifiedAt())
                    .view(room.getView())
                    .memberName(room.getMember().getName())
                    .commentCount(room.getComments().size())
                    .likeCount(room.getLikes().size())
                    .scrapCount(room.getScraps().size())
                    .roomImages(room.getRoomImages().stream().map(roomImage -> roomImage.getImage_url()).collect(Collectors.toList()))
                    .scrap(false)
                    .like(false)
                    .build();
        }
    }



}
