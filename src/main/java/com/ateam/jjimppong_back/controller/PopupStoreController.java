package com.ateam.jjimppong_back.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.jjimppong_back.common.entity.PopupStoreEntity;
import com.ateam.jjimppong_back.repository.PopupStoreRepository;

@RestController
@RequestMapping("/popup-stores")
public class PopupStoreController {
    
    private final PopupStoreRepository popupStoreRepository;
    
    public PopupStoreController(PopupStoreRepository popupStoreRepository) {
        this.popupStoreRepository = popupStoreRepository;   
    }

    @GetMapping()
    public List<PopupStoreEntity> getPopupStoreEntities(@RequestParam String region) {
        return popupStoreRepository.findByRegion(region);
    }
}
