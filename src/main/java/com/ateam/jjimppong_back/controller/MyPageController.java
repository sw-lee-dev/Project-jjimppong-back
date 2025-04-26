package com.ateam.jjimppong_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.jjimppong_back.common.dto.request.mypage.PasswordReCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PatchSignInUserRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PostNicknameCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetDetailMyBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyLevelResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyPageBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetSignInUserResponseDto;
import com.ateam.jjimppong_back.service.MyPageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/my-page")
@RequiredArgsConstructor
public class MyPageController {

  private final MyPageService myPageService;

  @PostMapping({"", "/"})
  public ResponseEntity<ResponseDto> passwordReCheck(
    @RequestBody @Valid PasswordReCheckRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = myPageService.passwordReCheck(requestBody, userId);
    return response;
  }

  @PostMapping("/my-main/nickname-check")
  public ResponseEntity<ResponseDto> updateNicknameCheck(
    @RequestBody @Valid PostNicknameCheckRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = myPageService.updateNicknameCheck(requestBody, userId);
    return response;
  }

  @PutMapping("/my-main")
  public ResponseEntity<ResponseDto> updateMyPageInfo(
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = myPageService.updateMyPageInfo(userId);
    return response;
  }

  @GetMapping("/my-main/level")
  public ResponseEntity<? super GetMyLevelResponseDto> getMyLevel(
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<? super GetMyLevelResponseDto> response = myPageService.getMyLevel(userId);
    return response;
  }

  @GetMapping("/my-main/my-boards")
  public ResponseEntity<? super GetMyPageBoardResponseDto> getMyPageBoard(
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<? super GetMyPageBoardResponseDto> response = myPageService.getMyPageBoard(userId);
    return response;
  }

  @GetMapping("/my-main/{boardNumber}")
  public ResponseEntity<? super GetDetailMyBoardResponseDto> getDetailMyBoard(
    @AuthenticationPrincipal String userId,
    @PathVariable("boardNumber") Integer boardNumber
  ) {
    ResponseEntity<? super GetDetailMyBoardResponseDto> response = myPageService.getDetailMyBoard(userId, boardNumber);
    return response;
  }

  @GetMapping("/my-main/user-info")
  public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<? super GetSignInUserResponseDto> response = myPageService.getSignInUser(userId);
    return response;
  }

  @PatchMapping("/my-main/user-info")
  public ResponseEntity<ResponseDto> patchSignInUser(
    @RequestBody @Valid PatchSignInUserRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = myPageService.patchSignInUser(requestBody, userId);
    return response;
  }
  
}
