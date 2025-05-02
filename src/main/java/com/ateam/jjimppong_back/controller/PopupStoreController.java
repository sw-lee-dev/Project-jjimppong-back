package com.ateam.jjimppong_back.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.jjimppong_back.common.entity.PopupStoreEntity;
import com.ateam.jjimppong_back.service.PopupStoreService;

@RestController
@RequestMapping("/popup-stores")
public class PopupStoreController {
    
    private final PopupStoreService popupStoreService;
    
    public PopupStoreController(PopupStoreService popupStoreService) {
        this.popupStoreService = popupStoreService;   
    }

    @GetMapping()
    public List<PopupStoreEntity> getPopupStore(@RequestParam String region) {
        return popupStoreService.findByAdmSectCode(region);
    }
}
