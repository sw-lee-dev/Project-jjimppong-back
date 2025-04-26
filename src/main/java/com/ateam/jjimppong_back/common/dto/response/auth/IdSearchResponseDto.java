package com.ateam.jjimppong_back.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class IdSearchResponseDto extends ResponseDto{

    private String userId;

    // 생성자
    private IdSearchResponseDto(String userId){ 
        this.userId = userId;
    }

    // 성공 응답 메서드 추가 (성공 메시지와 상태 코드 200)
    public static ResponseEntity<IdSearchResponseDto> success(String userId) {
        IdSearchResponseDto body = new IdSearchResponseDto(userId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
    
}
