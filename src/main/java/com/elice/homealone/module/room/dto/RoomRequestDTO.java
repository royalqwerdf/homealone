package com.elice.homealone.module.room.dto;

import com.elice.homealone.global.exception.ErrorMessage;
import com.elice.homealone.module.tag.dto.PostTagDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
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
    private List<PostTagDto> tags;
    @NotBlank(message = ErrorMessage.THUMBNAIL_URL_REQUIRED)
    private String thumbnailUrl;
    private List<String> roomImages = new ArrayList<>();

//    public static RoomRequestDTO toRoomRequestDTO(Room room){
//        return RoomRequestDTO.builder()
//                .title(room.getTitle())
//                .content(room.getContent())
//                .thumbnailUrl(room.getThumbnailUrl())
//                .build();
//    }

}
