package com.ateam.jjimppong_back.common.dto.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnsUserRequestDto {
    private String snsId;
    private String joinType;
    private String userId;
}