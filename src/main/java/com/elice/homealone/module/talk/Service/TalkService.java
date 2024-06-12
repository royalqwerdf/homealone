package com.elice.homealone.module.talk.Service;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.module.like.service.LikeService;
import com.elice.homealone.module.member.entity.Member;
import com.elice.homealone.module.member.service.AuthService;
import com.elice.homealone.module.scrap.service.ScrapService;
import com.elice.homealone.module.talk.entity.TalkImage;
import com.elice.homealone.module.talk.repository.TalkRepository;
import com.elice.homealone.module.post.repository.PostRepository;
import com.elice.homealone.module.tag.Service.PostTagService;
import com.elice.homealone.module.talk.dto.TalkRequestDTO;
import com.elice.homealone.module.talk.dto.TalkResponseDTO;
import com.elice.homealone.module.talk.entity.Talk;
import com.elice.homealone.module.talk.repository.TalkSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class TalkService {
    private final TalkRepository talkRepository;
    private final AuthService authService;
    private final PostTagService postTagService;
    private final LikeService likeService;
    private final ScrapService scrapService;
    private final TalkViewLogService talkViewLogService;
    private final PostRepository postRepository;
    @Transactional
    public TalkResponseDTO.TalkInfoDto CreateTalkPost(TalkRequestDTO talkDto){
        Member member = authService.getMember();
        Talk talk = new Talk(talkDto,member);
        //HTML태그 제거
        String plainContent = Jsoup.clean(talkDto.getContent(), Safelist.none()).replace("&nbsp;", " ").replaceAll("\\s", " ").trim();
        talk.setPlainContent(plainContent);
        talkRepository.save(talk);
        talkDto.getTags().stream().map(tags -> postTagService.createPostTag(tags))
                .forEach(postTag -> talk.addTag(postTag));
        return TalkResponseDTO.TalkInfoDto.toTalkInfoDto(talk);
    }

    @Transactional
    public TalkResponseDTO.TalkInfoDto EditTalkPost(Long talkId, TalkRequestDTO talkDto){
        Member member = authService.getMember();
        Talk talkOriginal = talkRepository.findById(talkId).orElseThrow(() -> new HomealoneException(ErrorCode.TALK_NOT_FOUND));
        if(talkOriginal.getMember().getId() != member.getId()){
           throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        talkOriginal.setTitle(talkDto.getTitle());
        List<TalkImage> talkImage = talkDto.getImages().stream()
                .map(url -> new TalkImage(url,talkOriginal))
                .collect(Collectors.toList());
        talkOriginal.getTalkImages().clear();
        talkOriginal.getTalkImages().addAll(talkImage);
        return TalkResponseDTO.TalkInfoDto.toTalkInfoDto(talkOriginal);
    }

    @Transactional
    public void deleteRoomPost(Long talkId){
        Member member = authService.getMember();
        Talk talkOriginal = talkRepository.findById(talkId)
                .orElseThrow(() ->new HomealoneException(ErrorCode.TALK_NOT_FOUND));

        boolean isAdmin = authService.isAdmin(member);
        if(!isAdmin && (talkOriginal.getMember().getId() != member.getId())){
            throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        talkRepository.delete(talkOriginal);
    }


    @Transactional
    public Page<TalkResponseDTO> searchTalkPost(String title,String content,String tag, Long memberId,Pageable pageable){
        Specification<Talk> spec = Specification.where(null);

            if ((title != null && !title.isEmpty()) || (content != null && !content.isEmpty())) {
                String keyword = (title != null && !title.isEmpty()) ? title : content;
                spec = spec.and(TalkSpecification.containsTitleOrContent(keyword));
            }
            else if (title != null && !title.isEmpty()) {
                spec = spec.and(TalkSpecification.containsTitle(title));
            }

            else if (content != null && !content.isEmpty()) {
                spec = spec.and(TalkSpecification.containsPlainContent(content));
            }
            else if(tag != null && !tag.isEmpty()){
                spec = spec.and(TalkSpecification.containsTag(tag));
            }

            if(memberId != null){
                spec = spec.and(TalkSpecification.hasMemberId(memberId));
            }

        Page<Talk> findTalks = talkRepository.findAll(spec, pageable);

        return findTalks.map(TalkResponseDTO::toTalkResponseDTO);
    }

    @Transactional
    public TalkResponseDTO.TalkInfoDto findByTalkId(Long talkId){
        Member member = authService.getMember();
        Talk talk = talkRepository.findById(talkId)
                .orElseThrow(() -> new HomealoneException(ErrorCode.ROOM_NOT_FOUND));
        talk.setView(talk.getView() + 1);
        talkViewLogService.logView(talk);
        TalkResponseDTO.TalkInfoDto talkInfoDto = TalkResponseDTO.TalkInfoDto.toTalkInfoDto(talk);
        if (member != null) {
            // TODO: 회원이 스크랩했는지 체크 로직 추가

            boolean likedByMember = likeService.isLikedByMember(talk, member);
            boolean scrapdeByMember = scrapService.isScrapdeByMember(talk.getId(), member.getId());
            talkInfoDto.setLike(likedByMember);
            talkInfoDto.setScrap(scrapdeByMember);
        }
        return talkInfoDto;
    }


    @Transactional
    public Page<TalkResponseDTO> findTopTalkByView(Pageable pageable){
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        Page<TalkResponseDTO> talkResponseDTO = talkViewLogService.findTopTalksByViewCountInLastWeek(oneWeekAgo, pageable).map(TalkResponseDTO :: toTalkResponseDTO);
        return talkResponseDTO;
    }
    @Transactional
    public Page<TalkResponseDTO> findTalkByMember(Pageable pageable){
        Member member = authService.getMember();
        Page<TalkResponseDTO> TalkResponse = talkRepository.findTalkByMember(member, pageable).map(TalkResponseDTO::toTalkResponseDTO);
        return TalkResponse;

    }
}
