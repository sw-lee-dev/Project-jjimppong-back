package com.ateam.jjimppong_back.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.jjimppong_back.common.entity.RestaurantEntity;
import com.ateam.jjimppong_back.repository.RestaurantRepository;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    } 

    @GetMapping()
    public List<RestaurantEntity> getRestaurantEntity(@RequestParam String region) {
        return restaurantRepository.findByRegion(region);
    }
}
