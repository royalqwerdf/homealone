package com.elice.homealone.support.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.elice.homealone.support.repository.supportRepository;
import com.elice.homealone.support.dto.supportDTO;
import com.elice.homealone.support.domain.support;
import com.elice.homealone.support.dto.supportsDTO;

@Service
public class supportService {
    @Autowired
    private final supportRepository SupportRepository;

    public supportService(supportRepository SupportRepository) {this.SupportRepository = SupportRepository;}

    public void deleteAll() {
        SupportRepository.deleteAll();
    }

    public void saveSupport(supportDTO supportdto) {SupportRepository.save(supportdto.toEntity());}

    // 전체 리스트 조회 - 페이지네이션
    public Page<supportsDTO> findAllSupports(Pageable pageable) {
        Page<support> supports  = SupportRepository.findAll(pageable);

        return supports.map(support -> toDTOs(support));
    }
    public supportsDTO toDTOs(support Support) {
        supportsDTO dto = new supportsDTO();

        dto.setId(Support.getId());
        dto.setPolyBizSjnm(Support.getPolyBizSjnm());

        return dto;
    }


    // 청년정책지원 상세 조회
    public supportDTO findSupportById(Long id) {
        return SupportRepository.findById(id).map(this::toDTO).orElse(null);
    }
    public supportDTO toDTO(support Support) {
        supportDTO dto = new supportDTO();

        dto.setId(Support.getId());
        dto.setPolyBizSjnm(Support.getPolyBizSjnm());
        dto.setPolyltcnCn(Support.getPolyltcnCn());
        dto.setSporCn(Support.getSporCn());
        dto.setSporScvl(Support.getSporScvl());
        dto.setBizPrdCn(Support.getBizPrdCn());
        dto.setRqutPrdCn(Support.getRqutPrdCn());
        dto.setAgeInfo(Support.getAgeInfo());
        dto.setMajrRqisCn(Support.getMajrRqisCn());
        dto.setEmpmSttsCn(Support.getEmpmSttsCn());
        dto.setSplzRlmRqisCn(Support.getSplzRlmRqisCn());
        dto.setSplzRlmRqisCn(Support.getSplzRlmRqisCn());
        dto.setAccRqisCn(Support.getAccRqisCn());
        dto.setPrcpCn(Support.getPrcpCn());
        dto.setAditRscn(Support.getAditRscn());
        dto.setPrcpLmttTrgtCn(Support.getPrcpLmttTrgtCn());
        dto.setRqutProcCn(Support.getRqutProcCn());
        dto.setPstnPaprCn(Support.getPstnPaprCn());
        dto.setJdgnPresCn(Support.getJdgnPresCn());
        dto.setRqutUrla(Support.getRqutUrla());
        dto.setRfcSiteUrla1(Support.getRfcSiteUrla1());
        dto.setRfcSiteUrla2(Support.getRfcSiteUrla2());
        dto.setMngtMson(Support.getMngtMson());
        dto.setMngtMrofCherCn(Support.getMngtMrofCherCn());
        dto.setCherCtpcCn(Support.getCherCtpcCn());
        dto.setCnsgNmor(Support.getCnsgNmor());
        dto.setTintCherCn(Support.getTintCherCn());
        dto.setTintCherCtpcCn(Support.getTintCherCtpcCn());
        dto.setEtct(Support.getEtct());
        return dto;
    }
}
