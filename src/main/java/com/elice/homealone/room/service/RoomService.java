package com.elice.homealone.room.service;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.room.dto.RoomRequestDTO;
import com.elice.homealone.room.dto.RoomResponseDTO;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.room.repository.RoomRepository;
import com.elice.homealone.room.repository.RoomSpecification;
import com.elice.homealone.tag.Service.PostTagService;
import com.elice.homealone.tag.entity.PostTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PostTagService postTagService;
    @Transactional
    public RoomResponseDTO.RoomInfoDto CreateRoomPost(RoomRequestDTO roomDto, String token){ ///회원 정의 추가해야함.
        if(token == null || token.isEmpty()){
            throw new HomealoneException(ErrorCode.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(//회원이 없을때 예외 던져주기
                ()-> new HomealoneException(ErrorCode.MEMBER_NOT_FOUND)
                 );
        Room room = new Room(roomDto,member);
        roomRepository.save(room);
        String plainContent = Jsoup.clean(roomDto.getContent(), Safelist.none());
        room.setPlainContent(plainContent);
        roomRepository.save(room);
        roomDto.getTags().stream().map(tag -> postTagService.createPostTag(tag))
                .forEach(postTag-> room.addTag(postTag));
        //HTML태그 제거
        return RoomResponseDTO.RoomInfoDto.toRoomInfoDto(room);
    }

    @Transactional
    public RoomResponseDTO.RoomInfoDto EditRoomPost(String token,Long roomId, RoomRequestDTO roomDto){
        if(token == null || token.isEmpty()){
            throw new HomealoneException(ErrorCode.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new HomealoneException(ErrorCode.MEMBER_NOT_FOUND)
        );
        Room roomOriginal = roomRepository.findById(roomId)
                .orElseThrow(() ->new HomealoneException(ErrorCode.ROOM_NOT_FOUND));
        if(roomOriginal.getMember() != member){
           throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        roomOriginal.setTitle(roomDto.getTitle());
        roomOriginal.setContent(roomDto.getContent());
        roomOriginal.setThumbnailUrl(roomDto.getThumbnailUrl());
        return RoomResponseDTO.RoomInfoDto.toRoomInfoDto(roomOriginal);
    }

    @Transactional
    public void deleteRoomPost(String token,Long roomId){
        if(token == null || token.isEmpty()){
            throw new HomealoneException(ErrorCode.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new HomealoneException(ErrorCode.MEMBER_NOT_FOUND)
        );
        Room roomOriginal = roomRepository.findById(roomId)
                .orElseThrow(() ->new HomealoneException(ErrorCode.ROOM_NOT_FOUND));
        if(roomOriginal.getMember() != member){
            throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        roomRepository.delete(roomOriginal);
    }


    @Transactional
    public Page<RoomResponseDTO> searchRoomPost(String title,String content, String tag,Long memberId,Pageable pageable){
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
            else if(tag != null && !tag.isEmpty()){
                spec =spec.and(RoomSpecification.containsTag(tag));
            }

            if(memberId != null){
                spec = spec.and(RoomSpecification.hasMemberId(memberId));
            }

            return roomRepository.findAll(spec, pageable).map(RoomResponseDTO ::toRoomResponseDTO);


    }

    @Transactional
    public RoomResponseDTO.RoomInfoDto findByRoomId(Long roomId){
         Room room = roomRepository.findById(roomId)
                .orElseThrow(() ->new HomealoneException(ErrorCode.ROOM_NOT_FOUND));
        room.setView(room.getView()+1);
        return  RoomResponseDTO.RoomInfoDto.toRoomInfoDto(room);

    }


    @Transactional
    public RoomResponseDTO.RoomInfoDtoForMember findByRoomIdForMember(Long roomId,String token){
            String email = jwtTokenProvider.getEmail(token);
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    ()-> new HomealoneException(ErrorCode.MEMBER_NOT_FOUND)
            );
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() ->new HomealoneException(ErrorCode.ROOM_NOT_FOUND));
            room.setView(room.getView()+1);
        RoomResponseDTO.RoomInfoDtoForMember roomInfoDtoForMember = RoomResponseDTO.RoomInfoDtoForMember.toRoomInfoDtoForMember(room);
            //TODO:회원 자신이 scrap,like 했는지 확인 로직 필요 일단은 true로
            roomInfoDtoForMember.setScrap(true);
            roomInfoDtoForMember.setLike(true);
            return roomInfoDtoForMember;

    }

}
