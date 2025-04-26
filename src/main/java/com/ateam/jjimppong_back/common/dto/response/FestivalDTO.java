package com.ateam.jjimppong_back.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FestivalDTO {
    private String title;
    private String startDate;
    private String endDate;
    private String image;
    private String address;
}
