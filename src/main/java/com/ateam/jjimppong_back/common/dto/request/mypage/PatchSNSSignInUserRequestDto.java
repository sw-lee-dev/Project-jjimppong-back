package com.ateam.jjimppong_back.common.dto.request.mypage;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// sns 사용자의 정보 수정 requestDto - sns 로그인 하면 password 값이 null 이기 때문에 수정할 때 password 입력 안함
public class PatchSNSSignInUserRequestDto {
  @NotBlank
  private String userNickname;
  @NotBlank
  private String address;
  private String detailAddress;
  private String profileImage;
}
