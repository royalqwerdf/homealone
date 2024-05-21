package com.elice.homealone.room.dto;

import com.elice.homealone.like.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private long id;
    private String content;
    private Date createdAt;
    private Date updateAt;
    private Integer view;
    private Long likeCount;
    private Long scrapCount;
    private String memberNickname;
    private Integer commentCount;

}
