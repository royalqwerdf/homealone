package com.elice.homealone.support.dto;

import com.elice.homealone.support.domain.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class supportDTO {
    private Long id;
    private String polyBizSjnm; // 정책명
    private String polyltcnCn; // 정책소개
    private String sporCn;  // 지원내용
    private String sporScvl;    // 지원규모
    private String bizPrdCn;    // 사업운영기간내용
    private String rqutPrdCn;   // 사업신청기간내용
    private String ageInfo; // 연령 정보
    private String majrRqisCn;  // 전공요건내용
    private String empmSttsCn;  // 취업상태내용
    private String splzRlmRqisCn;   // 특화분야내용
    private String accRqisCn;   // 학력요건내용
    private String prcpCn;  // 거주지및소득조건내용
    private String aditRscn;    // 추가단서사항내용
    private String prcpLmttTrgtCn;  // 참여제한대상내용
    private String rqutProcCn;  // 신청절차내용
    private String pstnPaprCn;  // 제출서류내용
    private String jdgnPresCn;  //  심사발표내용
    private String rqutUrla;    // 신청사이트주소
    private String rfcSiteUrla1;    // 참고사이트 URL주소1
    private String rfcSiteUrla2;    // 참고사이트 URL주소2
    private String mngtMson;    // 주관부처명
    private String mngtMrofCherCn;  // 주관부처담당자이름
    private String cherCtpcCn;  // 주관부처담당자연락처
    private String cnsgNmor;    //   운영기관명
    private String tintCherCn;  //  운영기관담당자이름
    private String tintCherCtpcCn;  //   운영기관담당자연락처
    private String etct;    // 기타사항

    public supportDTO(support supportsPage) {

    }

    public support toEntity() {
        return new support(polyBizSjnm,polyltcnCn,sporCn,sporScvl,bizPrdCn,
                rqutPrdCn,ageInfo,majrRqisCn,empmSttsCn,splzRlmRqisCn,accRqisCn,
                prcpCn,aditRscn,prcpLmttTrgtCn,rqutProcCn,pstnPaprCn,jdgnPresCn,
                rqutUrla,rfcSiteUrla1,rfcSiteUrla2,mngtMson,mngtMrofCherCn,
                cherCtpcCn,cnsgNmor,tintCherCn,tintCherCtpcCn,etct);
    }

    public supportDTO(String polyBizSjnm, String polyltcnCn, String sporCn, String sporScvl, String bizPrdCn,
                   String rqutPrdCn,String ageInfo, String majrRqisCn, String empmSttsCn, String splzRlmRqisCn,
                   String accRqisCn, String prcpCn, String aditRscn, String prcpLmttTrgtCn,
                   String rqutProcCn, String pstnPaprCn, String jdgnPresCn, String rqutUrla,
                   String rfcSiteUrla1, String rfcSiteUrla2, String mngtMson, String mngtMrofCherCn,
                   String cherCtpcCn, String cnsgNmor, String tintCherCn, String tintCherCtpcCn,String etct) {
        this.polyBizSjnm = polyBizSjnm;
        this.polyltcnCn = polyltcnCn;
        this.sporCn = sporCn;
        this.sporScvl = sporScvl;
        this.bizPrdCn = bizPrdCn;
        this.rqutPrdCn = rqutPrdCn;
        this.ageInfo = ageInfo;
        this.majrRqisCn = majrRqisCn;
        this.empmSttsCn = empmSttsCn;
        this.splzRlmRqisCn = splzRlmRqisCn;
        this.accRqisCn = accRqisCn;
        this.prcpCn = prcpCn;
        this.aditRscn = aditRscn;
        this.prcpLmttTrgtCn = prcpLmttTrgtCn;
        this.rqutProcCn = rqutProcCn;
        this.pstnPaprCn = pstnPaprCn;
        this.jdgnPresCn = jdgnPresCn;
        this.rqutUrla = rqutUrla;
        this.rfcSiteUrla1 = rfcSiteUrla1;
        this.rfcSiteUrla2 = rfcSiteUrla2;
        this.mngtMson = mngtMson;
        this.mngtMrofCherCn =  mngtMrofCherCn;
        this.cherCtpcCn = cherCtpcCn;
        this.cnsgNmor = cnsgNmor;
        this.tintCherCn = tintCherCn;
        this.tintCherCtpcCn = tintCherCtpcCn;
        this.etct = etct;
    }

}
