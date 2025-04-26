package com.ateam.jjimppong_back.service;

import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.request.auth.EmailAuthCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.EmailAuthRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.IdCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.IdSearchRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.NicknameCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.PasswordResetRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SignInRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SignUpRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SnsSignUpRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SnsUserInfoRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.auth.IdSearchResponseDto;
import com.ateam.jjimppong_back.common.dto.response.auth.IsExistingUserResponseDto;
import com.ateam.jjimppong_back.common.dto.response.auth.SignInResponseDto;


public interface AuthService {

    ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<ResponseDto> nicknameCheck(NicknameCheckRequestDto dto);
    ResponseEntity<ResponseDto> emailAuth(EmailAuthRequestDto dto);
    ResponseEntity<ResponseDto> emailAuthId(EmailAuthRequestDto dto);
    ResponseEntity<ResponseDto> emailAuthCheck(EmailAuthCheckRequestDto dto);
    ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<ResponseDto> snsSignUp(SnsSignUpRequestDto dto, String snsId, String joinType);
    ResponseEntity<? super IdSearchResponseDto> idSearch(IdSearchRequestDto dto);
    ResponseEntity<ResponseDto> passwordReset(PasswordResetRequestDto dto);
    ResponseEntity<IsExistingUserResponseDto> isExistingUser(SnsUserInfoRequestDto dto);

    // 와일드 카드형태로 입력
    // super : 본인과 부모타입 사용
    // extends : 본인과 자식타입 사용
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);

}
