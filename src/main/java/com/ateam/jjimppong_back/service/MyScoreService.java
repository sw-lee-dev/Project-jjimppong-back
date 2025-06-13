package com.ateam.jjimppong_back.service;

import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.request.mypage.PasswordReCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PatchSNSSignInUserRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PostNicknameCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PatchSignInUserRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyLevelResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyPageBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetSignInUserResponseDto;

public interface MyScoreService {
  ResponseEntity<ResponseDto> passwordReCheck(PasswordReCheckRequestDto dto, String userId);
  ResponseEntity<ResponseDto> updateNicknameCheck(PostNicknameCheckRequestDto dto, String userId);

  ResponseEntity<ResponseDto> updateMyPageScore(String userId);

  ResponseEntity<? super GetMyLevelResponseDto> getMyLevel(String userId);
  ResponseEntity<? super GetMyPageBoardResponseDto> getMyPageBoard(String userId);

  ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId);
  ResponseEntity<ResponseDto> patchSignInUser(PatchSignInUserRequestDto dto, String userId);
  ResponseEntity<ResponseDto> patchSNSSignInUser(PatchSNSSignInUserRequestDto dto, String userId);
}
