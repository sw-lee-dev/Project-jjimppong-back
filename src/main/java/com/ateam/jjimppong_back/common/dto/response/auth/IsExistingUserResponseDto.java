package com.ateam.jjimppong_back.common.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsExistingUserResponseDto extends ResponseDto{

    private boolean info; // true/false 값을 위한 필드 추가

    // 생성자 (code, message, success를 모두 설정)
    public IsExistingUserResponseDto(boolean info) {
        this.info = info;
    }

    public static ResponseEntity<IsExistingUserResponseDto> success(boolean info){
        IsExistingUserResponseDto body = new IsExistingUserResponseDto(info);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 실패 응답 - 존재하지않는 사용자
    public static ResponseEntity<IsExistingUserResponseDto> userNotFound(boolean info) {
        IsExistingUserResponseDto body = new IsExistingUserResponseDto(info);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 데이터베이스 에러 - false
    public static ResponseEntity<IsExistingUserResponseDto> databaseError(boolean info) {
        IsExistingUserResponseDto body = new IsExistingUserResponseDto(info);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}
