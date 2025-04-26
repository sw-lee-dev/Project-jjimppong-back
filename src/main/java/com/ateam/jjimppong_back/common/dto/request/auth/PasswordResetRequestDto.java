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
public class PasswordResetRequestDto {

    @NotBlank
    // 띄어쓰기 불가
    @Pattern(regexp="^[a-zA-Z0-9]{6,20}$")
    private String userId;
    
    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{4}$")
    private String authNumber;
}
