package com.ateam.jjimppong_back.common.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnsSignUpRequestDto {

    @NotBlank
    private String userNickname;

    @NotBlank
    @Pattern(regexp="^[가-힣]{2,5}$")
    private String name;

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{4}$")
    private String authNumber;

    @NotBlank
    private String address;

    private String detailAddress;

    @NotBlank
    private String gender;

    @NotBlank
    private String userLevel;

    private String profileImage;

    @NotBlank
    @Pattern(regexp="^(KAKAO|NAVER)$")
    private String joinType;

    @NotBlank
    private String snsId;
}
