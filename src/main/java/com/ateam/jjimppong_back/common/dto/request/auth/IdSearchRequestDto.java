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
public class IdSearchRequestDto {
    
    @NotBlank
    // 한글로만 이루어져야한다.
    @Pattern(regexp="^[가-힣]{2,5}$")
    private String name;

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{4}$")
    private String authNumber;

}
