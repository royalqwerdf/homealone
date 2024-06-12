package com.elice.homealone.module.talk.dto;

import com.elice.homealone.module.comment.entity.Comment;
import com.elice.homealone.module.talk.entity.Talk;
import com.elice.homealone.module.tag.dto.PostTagDto;
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
    private String contentSummary;
    private Integer likeCount;
    public static TalkResponseDTO toTalkResponseDTO(Talk talk){
        return TalkResponseDTO.builder()
                .id(talk.getId())
                .title(talk.getTitle())
                .memberName(talk.getMember().getName())
                .commentCount(talk.getComments().size())
                .createdAt(talk.getCreatedAt())
                .contentSummary(talk.getPlainContent().length() <= 80 ? talk.getPlainContent() : talk.getPlainContent().substring(0,80))
                .likeCount( talk.getLikes() != null ? talk.getLikes().size() : 0)
                .build();
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TalkInfoDto extends TalkResponseDTO {
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer view;
        private Integer likeCount;
        private Integer scrapCount;
        private String memberName;
        private Integer commentCount;
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
                    .likeCount(talk.getLikes().size())
                    .scrapCount(talk.getScraps().size())
                    .memberName(talk.getMember().getName())
                    .commentCount(talk.getComments().size())
                    .scrap(false)
                    .like(false)
                    .build();
        }
    }

}
