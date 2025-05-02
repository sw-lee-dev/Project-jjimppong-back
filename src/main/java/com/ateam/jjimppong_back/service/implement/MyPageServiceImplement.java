package com.ateam.jjimppong_back.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ateam.jjimppong_back.common.dto.request.mypage.PasswordReCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PostNicknameCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PatchSignInUserRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyLevelResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetMyPageBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.mypage.GetSignInUserResponseDto;
import com.ateam.jjimppong_back.common.entity.BoardEntity;
import com.ateam.jjimppong_back.common.entity.MyPageEntity;
import com.ateam.jjimppong_back.common.entity.UserEntity;
import com.ateam.jjimppong_back.repository.BoardRepository;
import com.ateam.jjimppong_back.repository.MyPageRepository;
import com.ateam.jjimppong_back.repository.UserRepository;
import com.ateam.jjimppong_back.service.MyPageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageServiceImplement implements MyPageService {

  private final UserRepository userRepository;
  private final BoardRepository boardRepository;
  private final MyPageRepository myPageRepository;
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
      String updateNickname = dto.getUpdateNickname();
      boolean isExistNickname = userRepository.existsByUserNickname(updateNickname);
      if (isExistNickname) return ResponseDto.existUser();
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ResponseDto> updateMyPageInfo(String userId) {
    
    try {
      UserEntity userEntity = userRepository.findByUserId(userId);
      MyPageEntity myPageEntity = userEntity.getMyPageEntity();
      if (myPageEntity == null) {
        myPageEntity = new MyPageEntity();
        myPageEntity.setUserEntity(userEntity);
        userEntity.setMyPageEntity(myPageEntity);
      }

      Integer totalScore = boardRepository.sumBoardScoreByUserId(userId);
      myPageEntity.setUserScore(totalScore);
      myPageEntity.setUserLevel(myPageEntity.getLevel().getNumericValue());
      userEntity.setUserLevel(myPageEntity.getLevel().getNumericValue());
      myPageRepository.save(myPageEntity);
      userRepository.save(userEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<? super GetMyLevelResponseDto> getMyLevel(String userId) {
    MyPageEntity myPageEntity = null;

    try {
      myPageEntity = myPageRepository.findByUserId(userId);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetMyLevelResponseDto.success(myPageEntity);
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
  
  // @Override
  // public ResponseEntity<? super GetDetailMyBoardResponseDto> getDetailMyBoard(String userId, Integer boardNumber) {
  //   BoardEntity boardEntity = null;

  //   try {
  //     boardEntity = boardRepository.findByBoardNumber(boardNumber);
  //     if (boardEntity == null) return ResponseDto.noExistBoard();
  //   } catch (Exception exception) {
  //     exception.printStackTrace();
  //     return ResponseDto.databaseError();
  //   }

  //   return GetDetailMyBoardResponseDto.success(boardEntity);
  // }

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

      String userNickname = dto.getUserNickname();
      boolean isExist = userRepository.existsByUserNickname(userNickname);
      if (isExist) return ResponseDto.existUser();

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

}
