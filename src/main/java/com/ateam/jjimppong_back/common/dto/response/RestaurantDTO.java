package com.ateam.jjimppong_back.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    

    private String restaurantTitle;
    private String restaurantAddress;

}
