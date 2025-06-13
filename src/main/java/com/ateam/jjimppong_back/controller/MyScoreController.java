package com.ateam.jjimppong_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.jjimppong_back.common.dto.request.mypage.PasswordReCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PatchSNSSignInUserRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PatchSignInUserRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PostNicknameCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyLevelResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyPageBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetSignInUserResponseDto;
import com.ateam.jjimppong_back.service.MyScoreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/my-page")
@RequiredArgsConstructor
public class MyScoreController {

  private final MyScoreService myScoreService;

  @PostMapping({"", "/"})
  public ResponseEntity<ResponseDto> passwordReCheck(
    @RequestBody @Valid PasswordReCheckRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = myScoreService.passwordReCheck(requestBody, userId);
    return response;
  }

  @PostMapping("/my-main/nickname-check")
  public ResponseEntity<ResponseDto> updateNicknameCheck(
    @RequestBody @Valid PostNicknameCheckRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = myScoreService.updateNicknameCheck(requestBody, userId);
    return response;
  }

  @PutMapping("/my-main")
  public ResponseEntity<ResponseDto> updateMyPageScore(
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = myScoreService.updateMyPageScore(userId);
    return response;
  }

  @GetMapping("/my-main/level")
  public ResponseEntity<? super GetMyLevelResponseDto> getMyLevel(
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<? super GetMyLevelResponseDto> response = myScoreService.getMyLevel(userId);
    return response;
  }

  @GetMapping("/my-main/my-boards")
  public ResponseEntity<? super GetMyPageBoardResponseDto> getMyPageBoard(
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<? super GetMyPageBoardResponseDto> response = myScoreService.getMyPageBoard(userId);
    return response;
  }

  @GetMapping("/my-main/user-info")
  public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<? super GetSignInUserResponseDto> response = myScoreService.getSignInUser(userId);
    return response;
  }

  @PatchMapping("/my-main/user-info")
  public ResponseEntity<ResponseDto> patchSignInUser(
    @RequestBody @Valid PatchSignInUserRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = myScoreService.patchSignInUser(requestBody, userId);
    return response;
  }

  // sns 사용자 정보 수정 - sns 로그인 하면 password 값이 null 이기 때문에 patchSNSSignInUser 추가
  @PatchMapping("/my-main/sns-user-info")
  public ResponseEntity<ResponseDto> patchSNSSignInUser(
    @RequestBody @Valid PatchSNSSignInUserRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = myScoreService.patchSNSSignInUser(requestBody, userId);
    return response;
  }
  
}
