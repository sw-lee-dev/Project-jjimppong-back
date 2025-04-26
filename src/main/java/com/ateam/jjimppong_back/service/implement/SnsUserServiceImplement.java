package com.ateam.jjimppong_back.service.implement;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.entity.SnsUserEntity;
import com.ateam.jjimppong_back.common.entity.UserEntity;
import com.ateam.jjimppong_back.repository.SnsUserRepository;
import com.ateam.jjimppong_back.repository.UserRepository;
import com.ateam.jjimppong_back.service.SnsUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class SnsUserServiceImplement implements SnsUserService {

    private final SnsUserRepository snsUserRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseDto> saveSnsUser(String snsId, String joinType, String userId) {
        try {
            // SNS 로그인 정보가 이미 있는지 확인
            Optional<SnsUserEntity> snsUser = snsUserRepository.findBySnsIdAndJoinType(snsId, joinType);

            if (!snsUser.isPresent()) {
                // SNS 정보가 없으면 새로 생성
                SnsUserEntity newSnsUser = new SnsUserEntity(snsId, joinType);

                // user_id가 존재하면 UserEntity와 연결
                if (userId != null) {
                    UserEntity userEntity = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    newSnsUser.setUserEntity(userEntity);
                }

                // 새로운 SNS 사용자 저장
                snsUserRepository.save(newSnsUser);

                // 성공 응답 반환
                return ResponseDto.success(HttpStatus.OK);

            } else {
                // 이미 존재하는 SNS 사용자
                return ResponseDto.existUser();
            }

        } catch (Exception exception) {
            // 예외 발생 시 처리
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }
}