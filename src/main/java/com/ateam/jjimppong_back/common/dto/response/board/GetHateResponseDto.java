package com.ateam.jjimppong_back.common.dto.response.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.entity.HateEntity;

public class GetHateResponseDto extends ResponseDto{
    private List<String> goods;

    private GetHateResponseDto(List<HateEntity> hateEntities) {
        this.goods = new ArrayList<>();
        for (HateEntity hateEntity : hateEntities) {
            String userId = hateEntity.getUserId();
            this.goods.add(userId);
        }
    }

    public static ResponseEntity<GetHateResponseDto> success(List<HateEntity> hateEntities) {
        GetHateResponseDto body = new GetHateResponseDto(hateEntities);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
