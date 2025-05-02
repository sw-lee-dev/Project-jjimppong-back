package com.ateam.jjimppong_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.jjimppong_back.common.dto.request.auth.EmailAuthCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.EmailAuthRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.IdCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.IdSearchRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.NicknameCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.PasswordResetRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SignInRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SignUpRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SnsSignUpRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.auth.IdSearchResponseDto;
import com.ateam.jjimppong_back.common.dto.response.auth.SignInResponseDto;
import com.ateam.jjimppong_back.common.dto.response.auth.SnsSignUpResponseDto;
import com.ateam.jjimppong_back.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/id-check")
    public ResponseEntity<ResponseDto> idCheck(
        @RequestBody @Valid IdCheckRequestDto requestBody
    ){ 
        ResponseEntity<ResponseDto> response = authService.idCheck(requestBody);
        return response;
    }

    @PostMapping("/nickname-check")
    public ResponseEntity<ResponseDto> nicknameCheck(
        @RequestBody @Valid NicknameCheckRequestDto requestBody
    ){ 
        ResponseEntity<ResponseDto> response = authService.nicknameCheck(requestBody);
        return response;
    }

    @PostMapping("/email-auth")
    public ResponseEntity<ResponseDto> emailAuth( 
        @RequestBody @Valid EmailAuthRequestDto requestBody
    ){ 
        ResponseEntity<ResponseDto> response = authService.emailAuth(requestBody);
        return response;
    }

    @PostMapping("/email-auth-id")
    public ResponseEntity<ResponseDto> emailAuthId( 
        @RequestBody @Valid EmailAuthRequestDto requestBody
    ){ 
        ResponseEntity<ResponseDto> response = authService.emailAuthId(requestBody);
        return response;
    }

    @PostMapping("/email-auth-check")
    public ResponseEntity<ResponseDto> emailAuthCheck(
        @RequestBody @Valid EmailAuthCheckRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = authService.emailAuthCheck(requestBody);
        return response;
    }
    
    @PostMapping("/sign-up")
    ResponseEntity<ResponseDto> signUp(
        @RequestBody @Valid SignUpRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = authService.signUp(requestBody);
        return response;
    }

    // SNS 로그인 후 추가 정보 입력을 통한 회원가입 처리
    @PostMapping("/sns-sign-up")
    public ResponseEntity<? super SnsSignUpResponseDto> snsSignUp(
        @RequestBody @Valid SnsSignUpRequestDto requestBody
    ) {
        ResponseEntity<? super SnsSignUpResponseDto> response = authService.snsSignUp(requestBody);
        return response;
    }

    @PostMapping("/sign-in")
    ResponseEntity<? super SignInResponseDto> signIn(
        @RequestBody @Valid SignInRequestDto requestBody
    ){
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response;
    }
        
    @PostMapping("/id-search")
    ResponseEntity<? super IdSearchResponseDto> idSearch( 
        @RequestBody @Valid IdSearchRequestDto requestBody
    ){ 
        ResponseEntity<? super IdSearchResponseDto> response = authService.idSearch(requestBody);
        return response;
    }

    @PostMapping("/password-reset")
    ResponseEntity<ResponseDto> passwordReset( 
        @RequestBody @Valid PasswordResetRequestDto requestBody
    ){ 
        ResponseEntity<ResponseDto> response = authService.passwordReset(requestBody);
        return response;
    }

}
