package com.ateam.jjimppong_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ateam.jjimppong_back.common.entity.EmailAuthEntity;

// JJIMBBONG 데이터베이스의 email_auth_number 테이블 작업을 위한 리포지토리
@Repository
public interface EmailAuthNumberRepository extends JpaRepository<EmailAuthEntity, String> {
    
    boolean existsByUserEmailAndAuthNumber(String userEmail, String authNumber);

}
