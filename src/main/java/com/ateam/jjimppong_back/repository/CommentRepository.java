package com.ateam.jjimppong_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ateam.jjimppong_back.common.entity.CommentEntity;
import com.ateam.jjimppong_back.common.vo.CommentProjection;

import jakarta.transaction.Transactional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
  
  List<CommentEntity> findByBoardNumberOrderByCommentWriteDateDesc(Integer boardNumber);
  CommentEntity findByCommentNumber(Integer commentNumber);

  @Transactional
  void deleteByBoardNumber(Integer BoardNumber);


  @Query(value =
    "SELECT c.comment_number As commentNumber, " +
    "       c.comment_writer_id AS commentWriterId, " +
    "       c.board_Number AS boardNumber, " +
    "       c.comment_content AS commentContent, " +
    "       c.comment_write_date AS commentWriteDate, " +
    "       u.user_level AS userLevel, " +
    "       u.user_nickname AS userNickname " +
    "FROM comment c " +
    "INNER JOIN user u ON c.comment_writer_id = u.user_id " +
    "INNER JOIN board b ON c.board_number = b.board_number " +
    "WHERE c.board_number = :boardNumber " +
    "ORDER BY c.comment_write_date DESC",
    nativeQuery = true)
    List<CommentProjection> findAllByBoardNumberOrderByCommentWriteDateDesc(@Param("boardNumber") Integer boardNumber);

}