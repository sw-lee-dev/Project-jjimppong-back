package com.ateam.jjimppong_back.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class SnsSignUpResponseDto extends ResponseDto{
        
    // accessToken 
    private String accessToken;
    // 제한시간
    private Integer expiration;

    // 생성자
    private SnsSignUpResponseDto(String accessToken){
        this.accessToken = accessToken;
        // 9시간을 초로 계산
        this.expiration = 60 * 60 * 9;
    }

    public static ResponseEntity<SnsSignUpResponseDto> success(String accessToken){
        SnsSignUpResponseDto body = new SnsSignUpResponseDto(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
