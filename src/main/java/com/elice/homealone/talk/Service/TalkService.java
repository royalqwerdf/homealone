package com.elice.homealone.talk.Service;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.member.service.MemberService;
import com.elice.homealone.room.dto.RoomResponseDTO;
import com.elice.homealone.room.entity.RoomImage;
import com.elice.homealone.tag.Service.PostTagService;
import com.elice.homealone.tag.entity.PostTag;
import com.elice.homealone.talk.dto.TalkRequestDTO;
import com.elice.homealone.talk.dto.TalkResponseDTO;
import com.elice.homealone.talk.entity.Talk;
import com.elice.homealone.talk.entity.TalkImage;
import com.elice.homealone.talk.repository.TalkRepository;
import com.elice.homealone.talk.repository.TalkSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final MemberService memberService;
    private final PostTagService postTagService;
    @Transactional
    public TalkResponseDTO.TalkInfoDto CreateTalkPost(TalkRequestDTO talkDto, String email){ ///회원 정의 추가해야함.
        Member member = memberService.findByEmail(email);
        Talk talk = new Talk(talkDto,member);
        //HTML태그 제거
        String plainContent = Jsoup.clean(talkDto.getContent(), Safelist.none());
        talk.setPlainContent(plainContent);
        talkRepository.save(talk);
        talkDto.getTags().stream().map(tags -> postTagService.createPostTag(tags))
                .forEach(postTag -> talk.addTag(postTag));
        return TalkResponseDTO.TalkInfoDto.toTalkInfoDto(talk);
    }

    @Transactional
    public TalkResponseDTO.TalkInfoDto EditTalkPost(String email,Long talkId, TalkRequestDTO talkDto){
        Member member = memberService.findByEmail(email);
        Talk talkOriginal = talkRepository.findById(talkId).orElseThrow(() -> new HomealoneException(ErrorCode.TALK_NOT_FOUND));
        if(talkOriginal.getMember() != member){
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
    public void deleteRoomPost(String email,Long talkId){
        Member member = memberService.findByEmail(email);
        Talk talkOriginal = talkRepository.findById(talkId)
                .orElseThrow(() ->new HomealoneException(ErrorCode.TALK_NOT_FOUND));
        if(talkOriginal.getMember() != member){
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
            if(findTalks.isEmpty()){
                throw new HomealoneException(ErrorCode.SEARCH_NOT_FOUND);
            }

        return findTalks.map(TalkResponseDTO::toTalkResponseDTO);
    }

    @Transactional
    public TalkResponseDTO.TalkInfoDto findByTalkId(Long talkId, String email){
        if(email != null && !email.isEmpty()){
            Member member = memberService.findByEmail(email);
            Talk talk = talkRepository.findById(talkId)
                    .orElseThrow(() ->new HomealoneException(ErrorCode.TALK_NOT_FOUND));
            talk.setView(talk.getView()+1);
            TalkResponseDTO.TalkInfoDto talkInfoDtoForMember = TalkResponseDTO.TalkInfoDto.toTalkInfoDto(talk);
            //TODO:회원 자신이 scrap,like 했는지 확인 로직 필요 일단은 true로
            talkInfoDtoForMember.setScrap(true);
            talkInfoDtoForMember.setLike(true);
        }
        Talk talk = talkRepository.findById(talkId)
                .orElseThrow(() ->new HomealoneException(ErrorCode.TALK_NOT_FOUND));
        talk.setView(talk.getView()+1);
        return  TalkResponseDTO.TalkInfoDto.toTalkInfoDto(talk);

    }


    @Transactional
    public Page<TalkResponseDTO> findTopTalkByView(Pageable pageable){
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        Page<TalkResponseDTO> talkResponseDTO = talkRepository.findTopTalkByView(oneWeekAgo, pageable).map(TalkResponseDTO :: toTalkResponseDTO);
        return talkResponseDTO;
    }
    @Transactional
    public Page<TalkResponseDTO> findTalkByMember(String email, Pageable pageable){
        Member member = memberService.findByEmail(email);
        Page<TalkResponseDTO> TalkResponse = talkRepository.findTalkByMember(member, pageable).map(TalkResponseDTO::toTalkResponseDTO);
        if(TalkResponse.isEmpty()){
            throw new HomealoneException(ErrorCode.WRITE_NOT_FOUND);
        }
        return TalkResponse;

    }
}
