package com.ateam.jjimppong_back.service;

import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;

public interface SnsUserService {
    ResponseEntity<ResponseDto> saveSnsUser(String snsId, String joinType, String userId);
}