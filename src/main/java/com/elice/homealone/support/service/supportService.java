package com.elice.homealone.support.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elice.homealone.support.repository.supportRepository;
import com.elice.homealone.support.dto.supportDTO;

@Service
public class supportService {
    @Autowired
    private final supportRepository SupportRepository;

    public supportService(supportRepository SupportRepository) {this.SupportRepository = SupportRepository;}

    public void deleteAll() {
        SupportRepository.deleteAll();
    }

    public void saveSupport(supportDTO supportdto) {SupportRepository.save(supportdto.toEntity());}
}
