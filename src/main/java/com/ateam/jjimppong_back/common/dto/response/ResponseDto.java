package com.ateam.jjimppong_back.common.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
// access를 private로 설정
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto {
    private String code;
    private String message;

    // 생성자
    public ResponseDto() {
        this.code = ResponseCode.SUCCESS;
        this.message = ResponseMessage.SUCCESS;
    }

    // HTTP 응답 반환 메서드
    public static ResponseEntity<ResponseDto> success(HttpStatus status) {
        ResponseDto body = new ResponseDto();
        
        return ResponseEntity.status(status).body(body);
    }

    // SNS 추가 정보가 필요할 때 호출되는 메서드
    public static ResponseEntity<ResponseDto> snsNeedInfo() {
        ResponseDto body = new ResponseDto(ResponseCode.SNS_NEED_INFO, ResponseMessage.SNS_NEED_INFO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // 적절한 상태 코드 사용
    }

    public static ResponseEntity<ResponseDto> validationFail() {
        ResponseDto body = new ResponseDto(ResponseCode.VALIDATION_FAIL, ResponseMessage.VALIDATION_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto body = new ResponseDto(ResponseCode.SIGN_IN_FAIL,
        ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> existUser() {
        ResponseDto body = new ResponseDto(ResponseCode.EXIST_USER, ResponseMessage.EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto body = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> duplicatiedEmail() {
        ResponseDto body = new ResponseDto(ResponseCode.DUPLICATIED_EMAIL, ResponseMessage.DUPLICATIED_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> mailSendFail() {
        ResponseDto body = new ResponseDto(ResponseCode.MAIL_SEND_FAIL, ResponseMessage.MAIL_SEND_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> authFail() {
        ResponseDto body = new ResponseDto(ResponseCode.AUTH_FAIL, ResponseMessage.AUTH_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
  
    public static ResponseEntity<ResponseDto> duplicatedEmail() {
        ResponseDto body = new ResponseDto(ResponseCode.DUPLICATED_EMAIL, ResponseMessage.DUPLICATED_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistBoard() {
        ResponseDto body = new ResponseDto(ResponseCode.NO_EXIST_BOARD, ResponseMessage.NO_EXIST_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> passwordNotMatched() {
        ResponseDto body = new ResponseDto(ResponseCode.PASSWORD_NOT_MATCHED, ResponseMessage.PASSWORD_NOT_MATCHED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto body = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    public static ResponseEntity<ResponseDto> mailSendFailed() {
        ResponseDto body = new ResponseDto(ResponseCode.MAIL_SEND_FAILED, ResponseMessage.MAIL_SEND_FAILED);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    public static ResponseEntity<ResponseDto> userNotFound() {
        ResponseDto body = new ResponseDto(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistComment(){
        ResponseDto body = new ResponseDto(ResponseCode.NO_EXIST_COMMENT, ResponseMessage.NO_EXIST_COMMENT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

}
