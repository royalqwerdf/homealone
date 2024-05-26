package com.elice.homealone.room.dto;

import com.elice.homealone.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomSummaryDto {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String memberName;
    private Integer commentCount;
    private LocalDateTime createdAt;

    public static RoomSummaryDto toroomSummaryDto(Room room){
        return RoomSummaryDto.builder()
                .id(room.getId())
                .title(room.getTitle())
                .thumbnailUrl(room.getThumbnailUrl())
//                .memberName(room.getMember().getName())
                .commentCount(room.getComments().size())
                .createdAt(room.getCreatedAt())
                .build();
    }
}
