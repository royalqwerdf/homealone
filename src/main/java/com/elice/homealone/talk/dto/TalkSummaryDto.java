package com.elice.homealone.talk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalkSummaryDto {
    private Long id;
    private String title;
    private String memberName;
    private Integer commentCount;
}
