package com.elice.homealone.support.schedule;

import com.elice.homealone.support.dto.supportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.elice.homealone.support.service.supportService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Component
public class supportSchedule {
    @Autowired
    private final supportService SupportService;

    private int currentPageIndex = 1;

    private int pageCount = 0;

    public supportSchedule(supportService SupportService) { this.SupportService = SupportService; }

    @Scheduled(fixedDelay = 604800000) // 일주일마다 청년정책지원 데이터 삭제 및 저장 => 최신 데이터로 변경
    public void fetchSupportAPI() {

        // 청년정책지원 데이터 전체 삭제
        if(currentPageIndex == 1) { SupportService.deleteAll(); }

        saveSupportData();
    }

    private void saveSupportData() {
        String apiUrl = "https://www.youthcenter.go.kr/opi/youthPlcyList.do";
        do {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("openApiVlak", "6be9e0b4c4fa6153c08d8b1d")
                    .queryParam("display", 100)
                    .queryParam("pageIndex", currentPageIndex);

            // RestTemplate을 사용하여 API 호출
            RestTemplate restTemplate = new RestTemplate();
            String responseData = restTemplate.getForObject(builder.toUriString(), String.class);

            pageCount = parseSupportData(responseData);
            currentPageIndex++;
        }while(pageCount == 100);
        currentPageIndex=1;
    }

    private int parseSupportData(String responseData) {
        int count = 0;

        try {
            // XML 파싱 준비
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(responseData.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(input);

            // <youthPolicyList> 태그 값 추출
            NodeList nodeList = doc.getElementsByTagName("youthPolicy");
            count=nodeList.getLength();

            for(int i=0; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    supportDTO youngsupportDTO = new supportDTO();

                    // polyBizSjnm 태그 값 추출
                    NodeList polyBizSjnmList = ((Element) node).getElementsByTagName("polyBizSjnm");
                    if(polyBizSjnmList.getLength() > 0) {
                        youngsupportDTO.setPolyBizSjnm(polyBizSjnmList.item(0).getTextContent());
                    }

                    // polyItcnCn 태그 값 추출
                    NodeList polyItcnCnList = ((Element) node).getElementsByTagName("polyItcnCn");
                    if(polyItcnCnList.getLength() > 0) {
                        youngsupportDTO.setPolyltcnCn(polyItcnCnList.item(0).getTextContent());
                    }

                    // sporCn 태그 값 추출
                    NodeList sporCnList = ((Element) node).getElementsByTagName("sporCn");
                    if(sporCnList.getLength() > 0) {
                        youngsupportDTO.setSporCn(sporCnList.item(0).getTextContent());
                    }

                    // sporScvl 태그 값 추출
                    NodeList sporScvlList = ((Element) node).getElementsByTagName("sporScvl");
                    if(sporScvlList.getLength() > 0) {
                        youngsupportDTO.setSporScvl(sporScvlList.item(0).getTextContent());
                    }

                    // bizPrdCn 태그 값 추출
                    NodeList bizPrdCnList = ((Element) node).getElementsByTagName("bizPrdCn");
                    if(bizPrdCnList.getLength() > 0) {
                        youngsupportDTO.setBizPrdCn(bizPrdCnList.item(0).getTextContent());
                    }

                    // rqutPrdCn 태그 값 추출
                    NodeList rqutPrdCnList = ((Element) node).getElementsByTagName("rqutPrdCn");
                    if(rqutPrdCnList.getLength() > 0) {
                        youngsupportDTO.setRqutPrdCn(rqutPrdCnList.item(0).getTextContent());
                    }

                    // ageInfo 태그 값 추출
                    NodeList ageInfoList = ((Element) node).getElementsByTagName("ageInfo");
                    if(ageInfoList.getLength() > 0) {
                        youngsupportDTO.setAgeInfo(ageInfoList.item(0).getTextContent());
                    }

                    // majrRqisCn 태그 값 추출
                    NodeList majrRqisCnList = ((Element) node).getElementsByTagName("majrRqisCn");
                    if(majrRqisCnList.getLength() > 0) {
                        youngsupportDTO.setMajrRqisCn(majrRqisCnList.item(0).getTextContent());
                    }

                    // empmSttsCn 태그 값 추출
                    NodeList empmSttsCnList = ((Element) node).getElementsByTagName("empmSttsCn");
                    if(empmSttsCnList.getLength() > 0) {
                        youngsupportDTO.setEmpmSttsCn(empmSttsCnList.item(0).getTextContent());
                    }

                    // splzRlmRqisCn 태그 값 추출
                    NodeList splzRlmRqisCnList = ((Element) node).getElementsByTagName("splzRlmRqisCn");
                    if(splzRlmRqisCnList.getLength() > 0) {
                        youngsupportDTO.setSplzRlmRqisCn(splzRlmRqisCnList.item(0).getTextContent());
                    }

                    // accrRqisCn 태그 값 추출
                    NodeList accrRqisCnList = ((Element) node).getElementsByTagName("accrRqisCn");
                    if(accrRqisCnList.getLength() > 0) {
                        youngsupportDTO.setAccRqisCn(accrRqisCnList.item(0).getTextContent());
                    }

                    // prcpCn 태그 값 추출
                    NodeList prcpCnList = ((Element) node).getElementsByTagName("prcpCn");
                    if(prcpCnList.getLength() > 0) {
                        youngsupportDTO.setPrcpCn(prcpCnList.item(0).getTextContent());

                    }

                    // aditRscn 태그 값 추출
                    NodeList aditRscnList = ((Element) node).getElementsByTagName("aditRscn");
                    if(aditRscnList.getLength() > 0) {
                        youngsupportDTO.setAditRscn(aditRscnList.item(0).getTextContent());
                    }

                    // prcpLmttTrgtCn 태그 값 추출
                    NodeList prcpLmttTrgtCnList = ((Element) node).getElementsByTagName("prcpLmttTrgtCn");
                    if(prcpLmttTrgtCnList.getLength() > 0) {
                        youngsupportDTO.setPrcpLmttTrgtCn(prcpLmttTrgtCnList.item(0).getTextContent());
                    }

                    // rqutProcCn 태그 값 추출
                    NodeList rqutProcCnList = ((Element) node).getElementsByTagName("rqutProcCn");
                    if(rqutProcCnList.getLength() > 0) {
                        youngsupportDTO.setRqutProcCn(rqutProcCnList.item(0).getTextContent());
                    }

                    // pstnPaprCn 태그 값 추출
                    NodeList pstnPaprCnList = ((Element) node).getElementsByTagName("pstnPaprCn");
                    if(pstnPaprCnList.getLength() > 0) {
                        youngsupportDTO.setPstnPaprCn(pstnPaprCnList.item(0).getTextContent());
                    }

                    // jdgnPresCn 태그 값 추출
                    NodeList jdgnPresCnList = ((Element) node).getElementsByTagName("jdgnPresCn");
                    if(jdgnPresCnList.getLength() > 0) {
                        youngsupportDTO.setJdgnPresCn(jdgnPresCnList.item(0).getTextContent());
                    }

                    // rqutUrla 태그 값 추출
                    NodeList rqutUrlaList = ((Element) node).getElementsByTagName("rqutUrla");
                    if(rqutUrlaList.getLength() > 0) {
                        youngsupportDTO.setRqutUrla(rqutUrlaList.item(0).getTextContent());
                    }

                    // rfcSiteUrla1 태그 값 추출
                    NodeList rfcSiteUrla1List = ((Element) node).getElementsByTagName("rfcSiteUrla1");
                    if(rfcSiteUrla1List.getLength() > 0) {
                        youngsupportDTO.setRfcSiteUrla1(rfcSiteUrla1List.item(0).getTextContent());
                    }

                    // rfcSiteUrla2 태그 값 추출
                    NodeList rfcSiteUrla2List = ((Element) node).getElementsByTagName("rfcSiteUrla2");
                    if(rfcSiteUrla2List.getLength() > 0) {
                        youngsupportDTO.setRfcSiteUrla2(rfcSiteUrla2List.item(0).getTextContent());
                    }

                    // mngtMson 태그 값 추출
                    NodeList mngtMsonList = ((Element) node).getElementsByTagName("mngtMson");
                    if(mngtMsonList.getLength() > 0) {
                        youngsupportDTO.setMngtMson(mngtMsonList.item(0).getTextContent());
                    }

                    // mngtMrofCherCn 태그 값 추출
                    NodeList mngtMrofCherCnList = ((Element) node).getElementsByTagName("mngtMrofCherCn");
                    if(mngtMrofCherCnList.getLength() > 0) {
                        youngsupportDTO.setMngtMrofCherCn(mngtMrofCherCnList.item(0).getTextContent());
                    }

                    // cherCtpcCn 태그 값 추출
                    NodeList cherCtpcCnList = ((Element) node).getElementsByTagName("cherCtpcCn");
                    if(cherCtpcCnList.getLength() > 0) {
                        youngsupportDTO.setCherCtpcCn(cherCtpcCnList.item(0).getTextContent());
                    }

                    // cnsgNmor 태그 값 추출
                    NodeList cnsgNmorList = ((Element) node).getElementsByTagName("cnsgNmor");
                    if(cnsgNmorList.getLength() > 0) {
                        youngsupportDTO.setCnsgNmor(cnsgNmorList.item(0).getTextContent());
                    }

                    // tintCherCn 태그 값 추출
                    NodeList tintCherCnList = ((Element) node).getElementsByTagName("tintCherCn");
                    if(tintCherCnList.getLength() > 0) {
                        youngsupportDTO.setTintCherCn(tintCherCnList.item(0).getTextContent());
                    }

                    // tintCherCtpcCn 태그 값 추출
                    NodeList tintCherCtpcCnList = ((Element) node).getElementsByTagName("tintCherCtpcCn");
                    if(tintCherCtpcCnList.getLength() > 0) {
                        youngsupportDTO.setTintCherCtpcCn(tintCherCtpcCnList.item(0).getTextContent());
                    }

                    // etct 태그 값 추출
                    NodeList etctList = ((Element) node).getElementsByTagName("etct");
                    if(etctList.getLength() > 0) {
                        youngsupportDTO.setEtct(etctList.item(0).getTextContent());
                    }

                    // 청년정책 데이터 추가
                    SupportService.saveSupport(youngsupportDTO);
                }

            }
        } catch (Exception e) {

        }
        return count;
    }
}
