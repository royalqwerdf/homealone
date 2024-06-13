package com.elice.homealone.support.controller;

import com.elice.homealone.recipe.dto.RecipePageDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elice.homealone.support.service.supportService;
import com.elice.homealone.support.domain.support;
import com.elice.homealone.support.dto.supportDTO;
import com.elice.homealone.support.dto.supportsDTO;

import java.util.List;

@RestController
public class supportController {
    @Autowired
    private final supportService SupportService;

    public supportController(supportService SupportService) { this.SupportService = SupportService; }

    // 청년정책지원 페이지네이션으로 조회 (20개씩 보여지도록)
    @GetMapping("/api/supports")
    public ResponseEntity<Page<supportsDTO>>getSupportsList(@PageableDefault(page = 0, size = 20) Pageable pageable) {
        Page<supportsDTO> supportDTOs = SupportService.findAllSupports(pageable);

        return new ResponseEntity<>(supportDTOs, HttpStatus.OK);
    }


    // 청년정책지원 상세 조회
    @GetMapping("/api/supports/{id}")
    public ResponseEntity<supportDTO> getSupport(@PathVariable Long id) {
        supportDTO support = SupportService.findSupportById(id);

        if( support != null ) {
            return new ResponseEntity<>(support, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
