package com.elice.homealone.talk.Service;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.tag.Service.PostTagService;
import com.elice.homealone.tag.entity.PostTag;
import com.elice.homealone.talk.dto.TalkRequestDTO;
import com.elice.homealone.talk.dto.TalkResponseDTO;
import com.elice.homealone.talk.entity.Talk;
import com.elice.homealone.talk.repository.TalkRepository;
import com.elice.homealone.talk.repository.TalkSpecification;
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
public class TalkService {
    private final TalkRepository talkRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PostTagService postTagService;
    @Transactional
    public TalkResponseDTO.TalkInfoDto CreateTalkPost(TalkRequestDTO talkDto, String token){ ///회원 정의 추가해야함.
        if(token == null || token.isEmpty()){
            throw new HomealoneException(ErrorCode.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(//회원이 없을때 예외 던져주기
                ()-> new HomealoneException(ErrorCode.MEMBER_NOT_FOUND)
                 );
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
    public TalkResponseDTO.TalkInfoDto EditTalkPost(String token,Long talkId, TalkRequestDTO talkDto){
        if(token == null || token.isEmpty()){
            throw new HomealoneException(ErrorCode.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new HomealoneException(ErrorCode.MEMBER_NOT_FOUND)
        );
        Talk talkOriginal = talkRepository.findById(talkId).orElseThrow(() -> new HomealoneException(ErrorCode.TALK_NOT_FOUND));

        if(talkOriginal.getMember() != member){
           throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        talkOriginal.setTitle(talkDto.getTitle());
        talkOriginal.setContent(talkDto.getContent());
        return TalkResponseDTO.TalkInfoDto.toTalkInfoDto(talkOriginal);
    }

    @Transactional
    public void deleteRoomPost(String token,Long talkId){
        if(token == null || token.isEmpty()){
            throw new HomealoneException(ErrorCode.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new HomealoneException(ErrorCode.MEMBER_NOT_FOUND)
        );
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

            return talkRepository.findAll(spec, pageable).map(TalkResponseDTO::toTalkResponseDTO);


    }

    @Transactional
    public TalkResponseDTO.TalkInfoDto findByTalkId(Long talkId){
        Talk talk = talkRepository.findById(talkId)
                .orElseThrow(() ->new HomealoneException(ErrorCode.TALK_NOT_FOUND));
        talk.setView(talk.getView()+1);
        return  TalkResponseDTO.TalkInfoDto.toTalkInfoDto(talk);

    }


    @Transactional
    public TalkResponseDTO.TalkInfoDtoForMember findByTalkIdForMember(Long talkId, String token){
            String email = jwtTokenProvider.getEmail(token);
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    ()-> new HomealoneException(ErrorCode.MEMBER_NOT_FOUND)
            );
            Talk talk = talkRepository.findById(talkId)
                    .orElseThrow(() ->new HomealoneException(ErrorCode.TALK_NOT_FOUND));
            talk.setView(talk.getView()+1);
        TalkResponseDTO.TalkInfoDtoForMember talkInfoDtoForMember = TalkResponseDTO.TalkInfoDtoForMember.toTalkInfoDtoForMember(talk);
        //TODO:회원 자신이 scrap,like 했는지 확인 로직 필요 일단은 true로
            talkInfoDtoForMember.setScrap(true);
            talkInfoDtoForMember.setLike(true);
            return talkInfoDtoForMember;

    }

}
