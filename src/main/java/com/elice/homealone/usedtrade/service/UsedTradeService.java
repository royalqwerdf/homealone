package com.elice.homealone.usedtrade.service;

import com.elice.homealone.global.exception.ErrorCode;
import com.elice.homealone.global.exception.HomealoneException;
import com.elice.homealone.member.entity.Member;
import com.elice.homealone.member.repository.MemberRepository;
import com.elice.homealone.member.service.AuthService;
import com.elice.homealone.post.entity.Post;
import com.elice.homealone.usedtrade.dto.UsedTradeRequestDto;
import com.elice.homealone.usedtrade.dto.UsedTradeResponseDto;
import com.elice.homealone.usedtrade.entity.UsedTrade;
import com.elice.homealone.usedtrade.entity.UsedTradeImage;
import com.elice.homealone.usedtrade.repository.UsedTradeImageRepository;
import com.elice.homealone.usedtrade.repository.UsedTradeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsedTradeService {

    private final UsedTradeRepository usedTradeRepository;
    private final UsedTradeImageRepository usedTradeImageRepository;
    private final AuthService authService;

    //모든 중고거래 조회
    public Page<UsedTradeResponseDto> getAllUsedTrades(Pageable pageable) {

        Page<UsedTrade> usedTrades = usedTradeRepository.findAll(pageable);
        Page<UsedTradeResponseDto> usedTradesDto = usedTrades.map(UsedTrade::toAllListDto);

        return usedTradesDto;

    }
    //1개의 중고거래 게시글 조회
    public UsedTradeResponseDto getUsedTrade(Long id) {

        UsedTradeResponseDto responseDto = usedTradeRepository.findById(id).map(UsedTrade::toDto)
                .orElseThrow(() -> new HomealoneException(ErrorCode.USEDTRADE_NOT_FOUND));

        return responseDto;
    }


    //중고거래 게시글 수정
    public boolean modifyUsedTrade(Long id, UsedTradeRequestDto requestDto) {


        //로그인한 계정의 데이터를 가져옴
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //게시글의 데이터를 가져옴
        UsedTrade usedTrade = usedTradeRepository.findById(id).orElseThrow(()->new HomealoneException(ErrorCode.USEDTRADE_NOT_FOUND));

        //로그인한 유저와 게시글의 작성자가 일치하는지 검증
        if(!Objects.equals(usedTrade.getMember().getId(),member.getId())){
            throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }

        if(requestDto.getTitle()!=null){
            usedTrade.setTitle(requestDto.getTitle());
        }
        if(requestDto.getPrice()!=0){
            usedTrade.setPrice(requestDto.getPrice());
        }
        if(requestDto.getLocation()!=null){
            usedTrade.setLocation(requestDto.getLocation());
        }
        if(requestDto.getContent()!=null){
            usedTrade.setContent(requestDto.getContent());
        }
        usedTradeRepository.save(usedTrade);
        return true;
    }
    //중고거래 게시글 생성
    @Transactional
    public Long createUsedTrade(UsedTradeRequestDto requestDto) {

        //로그인한 계정의 데이터를 가져옴
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UsedTrade usedTrade = requestDto.toEntity();
        usedTrade.setMember(member);
        usedTrade.setType(Post.Type.USEDTRADE);
        UsedTrade saveData = usedTradeRepository.save(usedTrade);

        //dto의 이미지들을 이미지 레포지토리에 저장
        List<UsedTradeImage> usedTradeImage = requestDto.getImages();
        for(UsedTradeImage image: usedTradeImage){
            image.setUsedTrade(usedTrade);
        }
        usedTradeImageRepository.saveAll(usedTradeImage);

        return saveData.getId();
    }

    //중고거래 게시글 삭제
    @Transactional
    public boolean deleteUsedTrade(Long id) {

        //로그인한 계정의 데이터를 가져옴
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //게시글의 데이터를 가져옴
        UsedTrade usedTrade = usedTradeRepository.findById(id).orElseThrow(()->new HomealoneException(ErrorCode.USEDTRADE_NOT_FOUND));

        boolean isAdmin = authService.isAdmin(member);

        //로그인한 유저와 게시글의 작성자가 일치하는지 검증
        if(!isAdmin && !Objects.equals(usedTrade.getMember().getId(),member.getId())){
            throw new HomealoneException(ErrorCode.NOT_UNAUTHORIZED_ACTION);
        }

        usedTradeRepository.delete(usedTrade);
        return true;
    }

    //중고거래 검색
    public Page<UsedTradeResponseDto> searchUsedTrades(Pageable pageable,String title,String content,String location) {
        Page<UsedTrade> usedTrades = usedTradeRepository.findBySearchQuery(pageable,title,content,location);
        Page<UsedTradeResponseDto> responseDtos = usedTrades.map(UsedTrade::toAllListDto);
        return responseDtos;
    }


}
