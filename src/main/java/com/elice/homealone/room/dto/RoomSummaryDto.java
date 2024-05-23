package com.elice.homealone.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomSummaryDto {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String memberName;
    private Integer commentCount;
}
