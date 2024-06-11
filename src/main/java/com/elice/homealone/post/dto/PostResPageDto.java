package com.elice.homealone.post.dto;

import com.elice.homealone.recipe.dto.RecipePageDto;
import com.elice.homealone.room.dto.RoomResponseDTO;
import com.elice.homealone.talk.dto.TalkResponseDTO;
import org.springframework.data.domain.Page;

public class PostResPageDto {

    private int PageNum;

    Page<RecipePageDto> recipePageDto;
    Page<RoomResponseDTO> roomPageDto;
    Page<TalkResponseDTO> talkPageDto;

}
