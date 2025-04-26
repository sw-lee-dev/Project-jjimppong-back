package com.ateam.jjimppong_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ateam.jjimppong_back.common.entity.CommentEntity;

import jakarta.transaction.Transactional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
  
  List<CommentEntity> findByBoardNumberOrderByWriteDateDesc(Integer boardNumber);

  @Transactional
  void deleteByBoardNumber(Integer BoardNumber);
}
