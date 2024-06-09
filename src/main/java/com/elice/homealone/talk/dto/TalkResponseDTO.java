package com.elice.homealone.talk.dto;

import com.elice.homealone.comment.entity.Comment;
import com.elice.homealone.talk.entity.Talk;
import com.elice.homealone.tag.dto.PostTagDto;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TalkResponseDTO {
    private Long id;
    private String title;
    private String memberName;
    private Integer commentCount;
    private LocalDateTime createdAt;

    public static TalkResponseDTO toTalkResponseDTO(Talk talk){
        return TalkResponseDTO.builder()
                .id(talk.getId())
                .title(talk.getTitle())
                .memberName(talk.getMember().getName())
                .commentCount(talk.getComments().size())
                .createdAt(talk.getCreatedAt())
                .build();
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TalkInfoDto extends TalkResponseDTO {
        @JsonRawValue
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer view;
        private Integer likeCount;
        private Integer scrapCount;
        private String memberName;
        private Integer commentCount;
        private List<Comment> comments;
        private List<PostTagDto> tags;
        private Boolean scrap;
        private Boolean like;
        public static TalkInfoDto toTalkInfoDto(Talk talk) {
            return TalkInfoDto.builder()
                    .id(talk.getId())
                    .title(talk.getTitle())
                    .tags(talk.getTags().stream().map(postTag -> postTag.toDto()).collect(Collectors.toList()))
                    .content(talk.getContent())
                    .createdAt(talk.getCreatedAt())
                    .updatedAt(talk.getModifiedAt())
                    .view(talk.getView())
                    .likeCount( talk.getPostLikes() != null ? talk.getPostLikes().size() : 0)
                    .scrapCount(talk.getScraps() != null ? talk.getScraps().size() : 0)
                    .memberName(talk.getMember().getName())
                    .commentCount(talk.getComments() != null ? talk.getComments().size() : 0)
                    .scrap(false)
                    .like(false)
                    .build();
        }
    }

}
