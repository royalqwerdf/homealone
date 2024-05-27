package com.elice.homealone.room.service;

import com.elice.homealone.global.exception.ErrorMessage;
import com.elice.homealone.global.exception.RoomException;
import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
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

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public RoomDto.RoomInfoDto CreateRoomPost(RoomDto roomDto,String token){ ///회원 정의 추가해야함.
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(//회원이 없을때 예외 던져주기
                 );
        Room room = new Room(roomDto,member);
        //HTML태그 제거
        String plainContent = Jsoup.clean(roomDto.getContent(), Safelist.none());
        room.setPlainContent(plainContent);
        roomRepository.save(room);
        return RoomDto.RoomInfoDto.toRoomInfoDto(room);
    }

    @Transactional
    public RoomDto.RoomInfoDto EditRoomPost(String token,Long roomId, RoomDto roomDto){
        if(token == null || token.isEmpty()){
            throw new RoomException.UnauthorizedActionException(ErrorMessage.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(//TODO:회원이 없을때 예외 던져주기
        );
        Room roomOriginal = roomRepository.findById(roomId)
                .orElseThrow(() ->new RoomException.RoomNotFoundException(ErrorMessage.ROOM_NOT_FOUND));
        if(roomOriginal.getMember() != member){
           throw new RoomException.UnauthorizedActionException(ErrorMessage.NOT_UNAUTHORIZED_ACTION);
        }
        roomOriginal.setTitle(roomDto.getTitle());
        roomOriginal.setContent(roomDto.getContent());
        roomOriginal.setThumbnailUrl(roomDto.getThumbnailUrl());
        return RoomDto.RoomInfoDto.toRoomInfoDto(roomOriginal);
    }

    @Transactional
    public void deleteRoomPost(String token,Long roomId){
        if(token == null || token.isEmpty()){
            throw new RoomException.UnauthorizedActionException(ErrorMessage.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(//TODO:회원이 없을때 예외 던져주기
        );
        Room roomOriginal = roomRepository.findById(roomId)
                .orElseThrow(() ->new RoomException.RoomNotFoundException(ErrorMessage.ROOM_NOT_FOUND));
        if(roomOriginal.getMember() != member){
            throw new RoomException.UnauthorizedActionException(ErrorMessage.NOT_UNAUTHORIZED_ACTION);
        }
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
                .orElseThrow(() ->new RoomException.RoomNotFoundException(ErrorMessage.ROOM_NOT_FOUND));
        room.setView(room.getView()+1);
        return RoomDto.RoomInfoDto.toRoomInfoDto(room);
    }

}
