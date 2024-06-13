package com.elice.homealone.module.room.service;


import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.module.comment.entity.Comment;
import com.elice.homealone.module.comment.repository.CommentRepository;
import com.elice.homealone.module.like.entity.Like;
import com.elice.homealone.module.like.repository.LikeRepository;
import com.elice.homealone.module.like.service.LikeService;
import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.member.service.AuthService;
import com.elice.homealone.module.post.repository.PostRepository;
import com.elice.homealone.module.room.repository.RoomImageRepository;
import com.elice.homealone.module.room.repository.RoomRepository;
import com.elice.homealone.module.scrap.entity.Scrap;
import com.elice.homealone.module.scrap.repository.ScrapRepository;
import com.elice.homealone.module.scrap.service.ScrapService;
import com.elice.homealone.module.post.entity.Post;
import com.elice.homealone.module.room.dto.RoomRequestDTO;
import com.elice.homealone.module.room.dto.RoomResponseDTO;
import com.elice.homealone.module.room.entity.Room;
import com.elice.homealone.module.room.entity.RoomImage;
import com.elice.homealone.module.room.repository.RoomSpecification;
import com.elice.homealone.module.tag.Repository.PostTagRepository;
import com.elice.homealone.module.tag.Service.PostTagService;
import com.elice.homealone.module.tag.entity.PostTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringEscapeUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final PostTagService postTagService;
//    private final ImageService imageService;
    private final LikeService likeService;
    private final ScrapService scrapService;
    private final RoomViewLogService roomViewLogService;
    private final AuthService authService;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final ScrapRepository scrapRepository;
    private final PostTagRepository postTagRepository;
    @Transactional
    public RoomResponseDTO.RoomInfoDto CreateRoomPost(RoomRequestDTO roomDto){
        Member member = authService.getMember();
        Room room = new Room(roomDto,member);
        Room save = roomRepository.save(room);
        //HTML태그 제거
        String plainContent = Jsoup.clean(roomDto.getContent(), Safelist.none()).replace("&nbsp;", " ").replaceAll("\\s", " ").trim();
        save.setContent(StringEscapeUtils.unescapeHtml4(roomDto.getContent()));
        save.setPlainContent(plainContent);
        roomDto.getTags().stream().map(tag -> postTagService.createPostTag(tag))
                .forEach(postTag-> save.addTag(postTag));

        return RoomResponseDTO.RoomInfoDto.toRoomInfoDto(save);
    }
    @Transactional
    public RoomResponseDTO.RoomInfoDto EditRoomPost(Long roomId, RoomRequestDTO roomDto){
        Member member = authService.getMember();
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
        Member member = authService.getMember();
        Room roomOriginal = roomRepository.findById(roomId)
                .orElseThrow(() ->new HomealoneException(ErrorCode.ROOM_NOT_FOUND));

        boolean isAdmin = authService.isAdmin(member);
        if(!isAdmin && roomOriginal.getMember().getId() != member.getId()){
            throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }

        //이전의 이미지url로 스토리지에 저장된 이미지 삭제
//        roomOriginal.getRoomImages().stream().forEach(roomImage -> imageService.deleteImage(roomImage.getImage_url()));
        List<Comment> commentsToDelete = new ArrayList<>(roomOriginal.getComments());
        List<PostTag> tagsToDelete = new ArrayList<>(roomOriginal.getTags());
        List<Scrap> scrapsToDelete = new ArrayList<>(roomOriginal.getScraps());
        List<Like> likesToDelete = new ArrayList<>(roomOriginal.getLikes());

        for (Comment comment : commentsToDelete) {
            commentRepository.delete(comment);
        }
        for (PostTag tag : tagsToDelete) {
            postTagRepository.delete(tag);
        }
        for (Scrap scrap : scrapsToDelete) {
            scrapRepository.delete(scrap);
        }
        for (Like like : likesToDelete) {
            likeRepository.delete(like);
        }

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
        Page<RoomResponseDTO> roomResponseDTOS =findRoom.map(room -> {
            RoomResponseDTO roomResponseDTO = RoomResponseDTO.toRoomResponseDTO(room);
            return roomResponseDTO;
        });
        return roomResponseDTOS;

    }
    @Transactional
    public RoomResponseDTO.RoomInfoDto findByRoomId(Long roomId) {
        Member member = authService.getMember();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new HomealoneException(ErrorCode.ROOM_NOT_FOUND));
        room.setView(room.getView() + 1);
        roomViewLogService.logView(room);
        RoomResponseDTO.RoomInfoDto roomInfoDto = RoomResponseDTO.RoomInfoDto.toRoomInfoDto(room);
        if (member != null) {
            // TODO: 회원이 스크랩했는지 체크 로직 추가
            boolean likedByMember = likeService.isLikedByMember(room, member);
            boolean scrapdeByMember = scrapService.isScrapdeByMember(room.getId(), member.getId());
            roomInfoDto.setLike(likedByMember);
            roomInfoDto.setScrap(scrapdeByMember);
        }

        return roomInfoDto;
    }
    @Transactional
    public List<RoomResponseDTO> findTopRoomByView(){
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        List<RoomResponseDTO> roomResponseDTOS = roomViewLogService.findTopRoomsByViewCountInLastWeek(oneWeekAgo).stream().map(RoomResponseDTO::toRoomResponseDTO).collect(Collectors.toList());
        if(roomResponseDTOS.isEmpty()){
            roomResponseDTOS  = roomRepository.findTop4ByOrderByViewDesc().stream().map(RoomResponseDTO::toRoomResponseDTO).collect(Collectors.toList());
        }

        return roomResponseDTOS;
    }

    @Transactional
    public Page<RoomResponseDTO> findRoomByMember(Pageable pageable){
        Member member = authService.getMember();
        Page<RoomResponseDTO> roomResponseDTOS = roomRepository.findRoomByMember(member, pageable).map(RoomResponseDTO::toRoomResponseDTO);
        return roomResponseDTOS;

    }

    // 로그인 한 멤버가 스크랩 한 레시피를 반환 해준다.
    public Page<RoomResponseDTO> findByScrap(Pageable pageable) {
        Member member = authService.getMember();
        List<Scrap> scraps = scrapService.findByMemberIdAndPostType(member.getId(), Post.Type.ROOM);
        List<Room> rooms = scraps.stream()
            .map(scrap -> (Room) scrap.getPost())
            .toList();
        Page<Room> roomPage = PageableExecutionUtils.getPage(
            rooms,
            pageable,
            rooms::size
        );

        return roomPage.map(RoomResponseDTO::toRoomResponseDTO);
    }

}
