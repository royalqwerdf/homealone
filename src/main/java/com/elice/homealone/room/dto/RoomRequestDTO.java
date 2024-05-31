package com.elice.homealone.room.dto;

import com.elice.homealone.global.exception.ErrorMessage;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.room.entity.RoomImage;
import com.elice.homealone.tag.dto.PostTagDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDTO {
    @NotBlank(message = ErrorMessage.TITLE_REQUIRED)
    private String title;
    @NotBlank(message = ErrorMessage.CONTENT_REQUIRED)
    private String content;
    private Post.Type type;
    @NotEmpty(message = ErrorMessage.TAGS_REQUIRED)
    private List<PostTagDto> tags;
    @NotBlank(message = ErrorMessage.THUMBNAIL_URL_REQUIRED)
    private String thumbnailUrl;
    private List<String> images;

//    public static RoomRequestDTO toRoomRequestDTO(Room room){
//        return RoomRequestDTO.builder()
//                .title(room.getTitle())
//                .content(room.getContent())
//                .thumbnailUrl(room.getThumbnailUrl())
//                .build();
//    }

}
