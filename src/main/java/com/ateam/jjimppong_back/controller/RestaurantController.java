package com.ateam.jjimppong_back.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.jjimppong_back.common.entity.RestaurantEntity;
import com.ateam.jjimppong_back.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    } 

    @GetMapping()
    public List<RestaurantEntity> getRestaurants(@RequestParam String region) {
        return restaurantService.findByAdmSectCode(region);
    }
    
}
