package com.ateam.jjimppong_back.common.dto.response.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.entity.GoodEntity;

import lombok.Getter;

@Getter
public class GetGoodResponseDto extends ResponseDto{
    private List<String> goods;

    private GetGoodResponseDto(List<GoodEntity> goodEntities) {
        this.goods = new ArrayList<>();
        for (GoodEntity goodEntity : goodEntities) {
            String userId = goodEntity.getUserId();
            this.goods.add(userId);
        }
    }

    public static ResponseEntity<GetGoodResponseDto> success(List<GoodEntity> goodEntities) {
        GetGoodResponseDto body = new GetGoodResponseDto(goodEntities);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}

