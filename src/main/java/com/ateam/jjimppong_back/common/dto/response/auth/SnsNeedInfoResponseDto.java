package com.ateam.jjimppong_back.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class SnsNeedInfoResponseDto extends ResponseDto {

    private String snsId;
    private String joinType;

    private SnsNeedInfoResponseDto(String snsId, String joinType) {
        this.snsId = snsId;
        this.joinType = joinType;
    }

    public static ResponseEntity<SnsNeedInfoResponseDto> success(String snsId, String joinType) {
        SnsNeedInfoResponseDto body = new SnsNeedInfoResponseDto(snsId, joinType);
        // BadRequest with SNS_NEED_INFO
        return ResponseEntity.status(HttpStatus.OK).body(body); 
    }
}
