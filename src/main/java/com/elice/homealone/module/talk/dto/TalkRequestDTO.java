package com.elice.homealone.module.talk.dto;

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
public class TalkRequestDTO {
    @NotBlank(message = ErrorMessage.TITLE_REQUIRED)
    private String title;
    @NotBlank(message = ErrorMessage.CONTENT_REQUIRED)
    private String content;
    private List<PostTagDto> tags;

}
