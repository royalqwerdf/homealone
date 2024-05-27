package com.elice.homealone.talk.Service;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.homealoneException;
import com.elice.homealone.global.jwt.JwtTokenProvider;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.room.dto.RoomSummaryDto;
import com.elice.homealone.room.entity.Room;
import com.elice.homealone.room.repository.RoomSpecification;
import com.elice.homealone.talk.dto.TalkDto;
import com.elice.homealone.talk.dto.TalkSummaryDto;
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

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TalkService {
    private final TalkRepository talkRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TalkDto.TalkInfoDto CreateTalkPost(TalkDto talkDto, String token){ ///회원 정의 추가해야함.
        if(token == null || token.isEmpty()){
            throw new homealoneException(ErrorCode.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(//회원이 없을때 예외 던져주기
                ()-> new homealoneException(ErrorCode.MEMBER_NOT_FOUND)
                 );
        Talk talk = new Talk(talkDto,member);
        //HTML태그 제거
        String plainContent = Jsoup.clean(talkDto.getContent(), Safelist.none());
        talk.setPlainContent(plainContent);
        talkRepository.save(talk);
        return TalkDto.TalkInfoDto.toTalkInfoDto(talk);
    }

    @Transactional
    public TalkDto.TalkInfoDto EditTalkPost(String token,Long talkId, TalkDto talkDto){
        if(token == null || token.isEmpty()){
            throw new homealoneException(ErrorCode.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new homealoneException(ErrorCode.MEMBER_NOT_FOUND)
        );
        Talk talkOriginal = talkRepository.findById(talkId).orElseThrow(() -> new homealoneException(ErrorCode.TALK_NOT_FOUND));

        if(talkOriginal.getMember() != member){
           throw new homealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        talkOriginal.setTitle(talkDto.getTitle());
        talkOriginal.setContent(talkDto.getContent());
        return TalkDto.TalkInfoDto.toTalkInfoDto(talkOriginal);
    }

    @Transactional
    public void deleteRoomPost(String token,Long talkId){
        if(token == null || token.isEmpty()){
            throw new homealoneException(ErrorCode.NO_JWT_TOKEN);
        }
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new homealoneException(ErrorCode.MEMBER_NOT_FOUND)
        );
        Talk talkOriginal = talkRepository.findById(talkId)
                .orElseThrow(() ->new homealoneException(ErrorCode.TALK_NOT_FOUND));
        if(talkOriginal.getMember() != member){
            throw new homealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }
        talkRepository.delete(talkOriginal);
    }


    @Transactional
    public Page<TalkSummaryDto> searchTalkPost(String title,String content, Long memberId,Pageable pageable){
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

            if(memberId != null){
                spec = spec.and(TalkSpecification.hasMemberId(memberId));
            }

            return talkRepository.findAll(spec, pageable).map(TalkSummaryDto::totalkSummaryDto);


    }

    @Transactional
    public TalkDto.TalkInfoDto findByTalkId(Long talkId){
        Talk talk = talkRepository.findById(talkId)
                .orElseThrow(() ->new homealoneException(ErrorCode.TALK_NOT_FOUND));
        talk.setView(talk.getView()+1);
        return  TalkDto.TalkInfoDto.toTalkInfoDto(talk);

    }


    @Transactional
    public TalkDto.TalkInfoDtoForMember findByTalkIdForMember(Long talkId, String token){
            String email = jwtTokenProvider.getEmail(token);
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    ()-> new homealoneException(ErrorCode.MEMBER_NOT_FOUND)
            );
            Talk talk = talkRepository.findById(talkId)
                    .orElseThrow(() ->new homealoneException(ErrorCode.TALK_NOT_FOUND));
            talk.setView(talk.getView()+1);
        TalkDto.TalkInfoDtoForMember talkInfoDtoForMember = TalkDto.TalkInfoDtoForMember.toTalkInfoDtoForMember(talk);
        //TODO:회원 자신이 scrap,like 했는지 확인 로직 필요 일단은 true로
            talkInfoDtoForMember.setScrap(true);
            talkInfoDtoForMember.setLike(true);
            return talkInfoDtoForMember;

    }

}
