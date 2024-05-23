package com.elice.homealone.room.service;

import com.elice.homealone.member.entity.Member;
import com.elice.homealone.room.dto.RoomDto;
import com.elice.homealone.room.dto.RoomSummaryDto;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    @Transactional
    public RoomDto.RoomInfoDto CreateRoomPost(RoomDto roomDto){
        Room room = Room.toRoom(roomDto);
        roomRepository.save(room);
        return RoomDto.RoomInfoDto.toRoomInfoDto(room);
    }

    @Transactional
    public RoomDto.RoomInfoDto EditRoomPost(Long roomId, RoomDto roomDto){
        Room roomOriginal = roomRepository.findById(roomId).orElseThrow();
        roomOriginal.setTitle(roomOriginal.getTitle());
        roomOriginal.setContent(roomDto.getContent());
        roomOriginal.setThumbnailUrl(roomDto.getThumbnailUrl());
        return RoomDto.RoomInfoDto.toRoomInfoDto(roomOriginal);
    }

    @Transactional
    public void deleteRoomPost(Long roomId){
        Room roomOriginal = roomRepository.findById(roomId).orElseThrow();
        roomRepository.delete(roomOriginal);
    }

    @Transactional
    public Page<RoomSummaryDto> MemberRoomPost(Member member, Pageable pageable){
        Page<Room> roomsByMember = roomRepository.findByMember(member, pageable);
        return roomsByMember.map(RoomSummaryDto::toroomSummaryDto);
    }

    @Transactional
    public Page<RoomSummaryDto> searchRoomPost(String query,Pageable pageable){
        Page<Room> roomsBySearch = roomRepository.searchByTitleContainingORContentContaining(query, query, pageable);
        return roomsBySearch.map(RoomSummaryDto::toroomSummaryDto);

    }

    @Transactional
    public RoomDto.RoomInfoDto findByRoomId(Long roomId){
        Room room = roomRepository.findById(roomId).orElseThrow();
        return RoomDto.RoomInfoDto.toRoomInfoDto(room);
    }
}
