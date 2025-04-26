package com.ateam.jjimppong_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ateam.jjimppong_back.common.entity.MyPageEntity;

@Repository
public interface MyPageRepository extends JpaRepository<MyPageEntity, String> {
  
  MyPageEntity findByUserId(String userId);

  Integer countByUserId(String userId);

}
