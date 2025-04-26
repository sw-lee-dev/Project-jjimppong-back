package com.ateam.jjimppong_back.common.dto.request.mypage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchSignInUserRequestDto {
  @NotBlank
  private String userNickname;
  @NotBlank
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,13}$")
  private String userPassword;
  @NotBlank
  private String address;
  private String detailAddress;
  private String profileImage;
}
