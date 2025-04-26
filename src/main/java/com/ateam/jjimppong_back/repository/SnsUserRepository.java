package com.ateam.jjimppong_back.repository;

import com.ateam.jjimppong_back.common.entity.SnsUserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsUserRepository extends JpaRepository<SnsUserEntity, String> {

    // SNS의 user_id에 해당하는 모든 sns_user 정보 조회
    Optional<SnsUserEntity> findByUserEntity_UserId(String userId);
    Optional<SnsUserEntity> findBySnsIdAndJoinType(String snsId, String joinType);
}