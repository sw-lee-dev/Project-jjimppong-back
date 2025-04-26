package com.ateam.jjimppong_back.common.dto.request.mypage;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordReCheckRequestDto {
  @NotBlank
  private String inputPassword;
}
