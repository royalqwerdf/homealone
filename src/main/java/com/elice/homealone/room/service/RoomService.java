package com.elice.homealone.room.service;

import com.elice.homealone.room.dto.RoomDto;
import com.elice.homealone.room.dto.RoomSummaryDto;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.room.repository.RoomRepository;
import com.elice.homealone.room.repository.RoomSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    @Transactional
    public RoomDto.RoomInfoDto CreateRoomPost(RoomDto roomDto){ ///회원 정의 추가해야함.
        Room room = new Room(roomDto);
        //HTML태그 제거
        String plainContent = Jsoup.clean(roomDto.getContent(), Safelist.none());
        room.setPlainContent(plainContent);
        roomRepository.save(room);
        return RoomDto.RoomInfoDto.toRoomInfoDto(room);
    }

    @Transactional
    public RoomDto.RoomInfoDto EditRoomPost(Long roomId, RoomDto roomDto){
        Room roomOriginal = roomRepository.findById(roomId)
                .orElseThrow();
        roomOriginal.setTitle(roomDto.getTitle());
        roomOriginal.setContent(roomDto.getContent());
        roomOriginal.setThumbnailUrl(roomDto.getThumbnailUrl());
        return RoomDto.RoomInfoDto.toRoomInfoDto(roomOriginal);
    }

    @Transactional
    public void deleteRoomPost(Long roomId){
        Room roomOriginal = roomRepository.findById(roomId)
                .orElseThrow();
        roomRepository.delete(roomOriginal);
    }


    @Transactional
    public Page<RoomSummaryDto> searchRoomPost(String title,String content, Long memberId,Pageable pageable){
        Specification<Room> spec = Specification.where(null);

            if ((title != null && !title.isEmpty()) || (content != null && !content.isEmpty())) {
                String keyword = (title != null && !title.isEmpty()) ? title : content;
                spec = spec.and(RoomSpecification.containsTitleOrContent(keyword));
            }
            else if (title != null && !title.isEmpty()) {
                spec = spec.and(RoomSpecification.containsTitle(title));
            }

            else if (content != null && !content.isEmpty()) {
                spec = spec.and(RoomSpecification.containsPlainContent(content));
            }

            if(memberId != null){
                spec = spec.and(RoomSpecification.hasMemberId(memberId));
            }

            return roomRepository.findAll(spec, pageable).map(RoomSummaryDto ::toroomSummaryDto);


    }

    @Transactional
    public RoomDto.RoomInfoDto findByRoomId(Long roomId){
        Room room = roomRepository.findById(roomId)
                .orElseThrow();
        room.setView(room.getView()+1);
        return RoomDto.RoomInfoDto.toRoomInfoDto(room);
    }

}
