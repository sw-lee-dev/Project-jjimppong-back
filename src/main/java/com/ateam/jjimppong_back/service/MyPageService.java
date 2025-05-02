package com.ateam.jjimppong_back.service;

import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.request.mypage.PasswordReCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PostNicknameCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PatchSignInUserRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetDetailMyBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyLevelResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyPageBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetSignInUserResponseDto;

public interface MyPageService {
  ResponseEntity<ResponseDto> passwordReCheck(PasswordReCheckRequestDto dto, String userId);
  ResponseEntity<ResponseDto> updateNicknameCheck(PostNicknameCheckRequestDto dto, String userId);

  ResponseEntity<ResponseDto> updateMyPageInfo(String userId);

  ResponseEntity<? super GetMyLevelResponseDto> getMyLevel(String userId);
  ResponseEntity<? super GetMyPageBoardResponseDto> getMyPageBoard(String userId);
  
  // ResponseEntity<? super GetDetailMyBoardResponseDto> getDetailMyBoard(String userId, Integer boardNumber);

  ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId);
  ResponseEntity<ResponseDto> patchSignInUser(PatchSignInUserRequestDto dto, String userId);
}
