package com.elice.homealone.support.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elice.homealone.support.repository.userSupportRepository;
import com.elice.homealone.support.domain.userSupport;
import com.elice.homealone.support.dto.userSupportDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class userSupportService {
    @Autowired
    private final userSupportRepository userSupportRepository;

    public userSupportService(userSupportRepository userSupportRepository) { this.userSupportRepository = userSupportRepository;}

    public void saveUserSupport(userSupport userSupport) {
        userSupportRepository.save(userSupport);
    }

//    // 사용자가 찜한 청년정책지원 리스트
//    public List<userSupportDTO> findUserSupportsByUserId(Long userId) {
//        List<userSupport> userSupportList = userSupportRepository.findByUserId(userId);
//
//        return userSupportList.stream().map(this::toDTO).collect(Collectors.toList());
//    }
//    public userSupportDTO toDTO(userSupport userSupport) {
//        userSupportDTO dto = new userSupportDTO();
//
//        dto.setId(userSupport.getId());
//        dto.setSupport(userSupport.getSupport());
//
//        return dto;
//    }
}
