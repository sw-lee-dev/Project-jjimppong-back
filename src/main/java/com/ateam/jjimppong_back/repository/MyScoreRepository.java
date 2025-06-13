package com.ateam.jjimppong_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ateam.jjimppong_back.common.entity.MyScoreEntity;


@Repository
public interface MyScoreRepository extends JpaRepository<MyScoreEntity, String> {
  
  MyScoreEntity findByUserId(String userId);

}
