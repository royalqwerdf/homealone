package com.elice.homealone.room.service;


import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.room.dto.RoomRequestDTO;
import com.elice.homealone.room.dto.RoomResponseDTO;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.room.entity.RoomImage;
import com.elice.homealone.room.repository.RoomRepository;
import com.elice.homealone.room.repository.RoomSpecification;
import com.elice.homealone.tag.Service.PostTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberService memberService;
    private final PostTagService postTagService;
//    private final ImageService imageService;
    @Transactional
    public RoomResponseDTO.RoomInfoDto CreateRoomPost(RoomRequestDTO roomDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();
        Room room = new Room(roomDto,member);
        Room save = roomRepository.save(room);
        //HTML태그 제거
        String plainContent = Jsoup.clean(roomDto.getContent(), Safelist.none());
        save.setContent(StringEscapeUtils.unescapeHtml4(roomDto.getContent()));
        save.setPlainContent(plainContent);
        roomDto.getTags().stream().map(tag -> postTagService.createPostTag(tag))
                .forEach(postTag-> save.addTag(postTag));

        return RoomResponseDTO.RoomInfoDto.toRoomInfoDto(save);
    }
    @Transactional
    public RoomResponseDTO.RoomInfoDto EditRoomPost(Long roomId, RoomRequestDTO roomDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();
        Room roomOriginal = roomRepository.findById(roomId)
                .orElseThrow(() ->new HomealoneException(ErrorCode.ROOM_NOT_FOUND));
        if(roomOriginal.getMember().getId() != member.getId()){
           throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        roomOriginal.setTitle(roomDto.getTitle());
        roomOriginal.setContent(roomDto.getContent());
        roomOriginal.setThumbnailUrl(roomDto.getThumbnailUrl());

        String plainContent = Jsoup.clean(roomDto.getContent(),Safelist.none());
        roomOriginal.setPlainContent(plainContent);
        //이미지새로 생성후 이전 이미지 테이블 전체 삭제 후 새로운 이미지로 대체
        List<RoomImage> newImages = roomDto.getRoomImages().stream()
                .map(url -> new RoomImage(url,roomOriginal))
                .collect(Collectors.toList());
        roomOriginal.getRoomImages().clear();
        roomOriginal.getRoomImages().addAll(newImages);
        return RoomResponseDTO.RoomInfoDto.toRoomInfoDto(roomOriginal);
    }

    @Transactional
    public void deleteRoomPost(Long roomId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();
        Room roomOriginal = roomRepository.findById(roomId)
                .orElseThrow(() ->new HomealoneException(ErrorCode.ROOM_NOT_FOUND));
        if(roomOriginal.getMember().getId() != member.getId()){
            throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        //이전의 이미지url로 스토리지에 저장된 이미지 삭제
//        roomOriginal.getRoomImages().stream().forEach(roomImage -> imageService.deleteImage(roomImage.getImage_url()));
        roomRepository.delete(roomOriginal);
    }


    @Transactional
    public Page<RoomResponseDTO> searchRoomPost(String title,String content, String tag,String memberName,Pageable pageable){
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
            else if (memberName != null){
                spec = spec.and(RoomSpecification.hasMemberId(memberName));
            }

        Page<Room> findRoom = roomRepository.findAll(spec, pageable);
            if(findRoom.isEmpty()){
                throw new HomealoneException(ErrorCode.SEARCH_NOT_FOUND);
            }

        return findRoom.map(RoomResponseDTO:: toRoomResponseDTO);

    }
    @Transactional
    public RoomResponseDTO.RoomInfoDto findByRoomId(Long roomId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = null;

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
                member = (Member) authentication.getPrincipal();

        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new HomealoneException(ErrorCode.ROOM_NOT_FOUND));
        room.setView(room.getView() + 1);
        RoomResponseDTO.RoomInfoDto roomInfoDto = RoomResponseDTO.RoomInfoDto.toRoomInfoDto(room);

        if (member != null) {
            // TODO: 회원이 스크랩했는지 체크 로직 추가

            roomInfoDto.setLike(true);
            roomInfoDto.setScrap(true);
        }

        return roomInfoDto;
    }
    @Transactional
    public Page<RoomResponseDTO> findTopRoomByView(Pageable pageable){
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        Page<RoomResponseDTO> roomResponseDTOS = roomRepository.findTopRoomByView(oneWeekAgo, pageable).map(RoomResponseDTO::toRoomResponseDTO);

        return roomResponseDTOS;
    }

    @Transactional
    public Page<RoomResponseDTO> findRoomByMember(Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();
        Page<RoomResponseDTO> roomResponseDTOS = roomRepository.findRoomByMember(member, pageable).map(RoomResponseDTO::toRoomResponseDTO);
        if(roomResponseDTOS.isEmpty()){
            throw new HomealoneException(ErrorCode.WRITE_NOT_FOUND);
        }
        return roomResponseDTOS;

    }

}
