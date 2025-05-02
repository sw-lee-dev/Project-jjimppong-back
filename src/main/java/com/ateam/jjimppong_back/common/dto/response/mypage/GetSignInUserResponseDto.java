package com.ateam.jjimppong_back.common.dto.response.mypage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetSignInUserResponseDto extends ResponseDto {
  private String userId;
  private String userNickname;
  private Integer userLevel;
  private String userPassword;
  private String name;
  private String address;
  private String detailAddress;
  private String gender;
  private String profileImage;
  private String joinType;

  private GetSignInUserResponseDto(UserEntity userEntity) {
    this.userId = userEntity.getUserId();
    this.userNickname = userEntity.getUserNickname();
    this.userLevel = userEntity.getUserLevel();
    this.userPassword = userEntity.getUserPassword();
    this.name = userEntity.getName();
    this.address = userEntity.getAddress();
    this.detailAddress = userEntity.getDetailAddress();
    this.gender = userEntity.getGender();
    this.profileImage = userEntity.getProfileImage();
    this.joinType = userEntity.getJoinType();
  }

  public static ResponseEntity<GetSignInUserResponseDto> success (UserEntity userEntity) {
    GetSignInUserResponseDto body = new GetSignInUserResponseDto(userEntity);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
