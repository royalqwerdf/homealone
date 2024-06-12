package com.elice.homealone.module.post.dto;

import com.elice.homealone.module.recipe.dto.RecipePageDto;
import com.elice.homealone.module.room.dto.RoomResponseDTO;
import com.elice.homealone.module.talk.dto.TalkResponseDTO;
import org.springframework.data.domain.Page;

public class PostResPageDto {

    private int PageNum;

    Page<RecipePageDto> recipePageDto;
    Page<RoomResponseDTO> roomPageDto;
    Page<TalkResponseDTO> talkPageDto;

}
