package com.ateam.jjimppong_back.repository;

import com.ateam.jjimppong_back.common.dto.request.auth.SnsUserInfoRequestDto;
import com.ateam.jjimppong_back.common.dto.response.auth.IsExistingUserResponseDto;
import com.ateam.jjimppong_back.common.entity.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
    
    boolean existsByUserId(String userId);
    boolean existsByUserNickname(String userNickname);
    boolean existsByUserEmail(String userEmail);

    UserEntity findByUserId(String userId);
    UserEntity findByJoinTypeAndSnsId(String joinType, String snsId);
    UserEntity findByNameAndUserEmail(String name, String userEmail);
    UserEntity findByUserIdAndUserEmail(String userId, String userEmail);
    Optional<UserEntity> findBySnsIdAndJoinType(String snsId, String joinType);

}
