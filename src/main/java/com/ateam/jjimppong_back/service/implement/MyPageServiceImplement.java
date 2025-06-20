package com.ateam.jjimppong_back.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ateam.jjimppong_back.common.dto.request.mypage.PasswordReCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PatchSNSSignInUserRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PostNicknameCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PatchSignInUserRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyLevelResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyPageBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetSignInUserResponseDto;
import com.ateam.jjimppong_back.common.entity.BoardEntity;
import com.ateam.jjimppong_back.common.entity.MyScoreEntity;
import com.ateam.jjimppong_back.common.entity.UserEntity;
import com.ateam.jjimppong_back.repository.BoardRepository;
import com.ateam.jjimppong_back.repository.MyScoreRepository;
import com.ateam.jjimppong_back.repository.UserRepository;
import com.ateam.jjimppong_back.service.MyScoreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageServiceImplement implements MyScoreService {

  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final MyScoreRepository myScoreRepository;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  
  @Override
  public ResponseEntity<ResponseDto> passwordReCheck(PasswordReCheckRequestDto dto, String userId) {
    
    try {
      UserEntity userEntity = userRepository.findByUserId(userId);
      String inputPassword = dto.getInputPassword();
      String encodedPassword = userEntity.getUserPassword();
      boolean isMatch = passwordEncoder.matches(inputPassword, encodedPassword);
      if (!isMatch) return ResponseDto.passwordNotMatched();
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ResponseDto> updateNicknameCheck(PostNicknameCheckRequestDto dto, String userId) {
    
    try {
      UserEntity userEntity = userRepository.findByUserId(userId);
      String userNickname = userEntity.getUserNickname();
      String updateNickname = dto.getUpdateNickname();
      boolean isMatched = updateNickname.equals(userNickname);
      boolean isExistNickname = userRepository.existsByUserNickname(updateNickname);
      if (!isMatched) {
        if (isExistNickname) return ResponseDto.existUser();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ResponseDto> updateMyPageScore(String userId) {
    
    try {
      UserEntity userEntity = userRepository.findByUserId(userId);
      MyScoreEntity myScoreEntity = userEntity.getMyScoreEntity();
      if (myScoreEntity == null) {
        myScoreEntity = new MyScoreEntity();
        myScoreEntity.setUserEntity(userEntity);
        userEntity.setMyScoreEntity(myScoreEntity);
      }

      Integer totalScore = boardRepository.sumBoardScoreByUserId(userId);
      myScoreEntity.setUserScore(totalScore);
      myScoreEntity.setUserLevel(myScoreEntity.getLevel().getNumericValue());
      userEntity.setUserLevel(myScoreEntity.getLevel().getNumericValue());
      myScoreRepository.save(myScoreEntity);
      userRepository.save(userEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<? super GetMyLevelResponseDto> getMyLevel(String userId) {
    MyScoreEntity myScoreEntity = null;

    try {
      myScoreEntity = myScoreRepository.findByUserId(userId);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetMyLevelResponseDto.success(myScoreEntity);
  }
  
  @Override
  public ResponseEntity<? super GetMyPageBoardResponseDto> getMyPageBoard(String userId) {
    List<BoardEntity> boardEntities = new ArrayList<>();
    
    try {
      boardEntities = boardRepository.findByUserIdOrderByBoardWriteDateDescBoardNumberDesc(userId);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetMyPageBoardResponseDto.success(boardEntities);
  }
  
  @Override
  public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId) {
    UserEntity userEntity = null;

    try {
      userEntity = userRepository.findByUserId(userId);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetSignInUserResponseDto.success(userEntity);
  }

  @Override
  public ResponseEntity<ResponseDto> patchSignInUser(PatchSignInUserRequestDto dto, String userId) {
    
    try {
      UserEntity userEntity = userRepository.findByUserId(userId);
      String userNickname = userEntity.getUserNickname();
      String updateNickname = dto.getUserNickname();
      boolean isMatched = updateNickname.equals(userNickname);
      boolean isExistNickname = userRepository.existsByUserNickname(updateNickname);
      if (!isMatched) {
        if (isExistNickname) return ResponseDto.existUser();
      }

      String userPassword = dto.getUserPassword();
      String encodedPassword = passwordEncoder.encode(userPassword);
      dto.setUserPassword(encodedPassword);

      userEntity.patch(dto);
      userRepository.save(userEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.OK);
  }

  // sns 사용자의 정보 수정 - sns 로그인 하면 password 값이 null 이기 때문에 제외함
  @Override
  public ResponseEntity<ResponseDto> patchSNSSignInUser(PatchSNSSignInUserRequestDto dto, String userId) {

    try {
      UserEntity userEntity = userRepository.findByUserId(userId);
      String userNickname = userEntity.getUserNickname();
      String updateNickname = dto.getUserNickname();
      boolean isMatched = updateNickname.equals(userNickname);
      boolean isExistNickname = userRepository.existsByUserNickname(updateNickname);
      if (!isMatched) {
        if (isExistNickname) return ResponseDto.existUser();
      }

      userEntity.patchSNS(dto);
      userRepository.save(userEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.OK);
  }

}
