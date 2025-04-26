package com.ateam.jjimppong_back.common.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ateam.jjimppong_back.common.dto.request.board.PostCommentRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "comment")
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer commentNumber;
  private String userId;
  private Integer boardNumber;
  private String commentContent;
  private Integer userLevel;
  private String writeDate;

  public CommentEntity(PostCommentRequestDto dto, Integer boardNumber, String userId) {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
    this.userId = userId;
    this.boardNumber = boardNumber;
    this.writeDate = now.format(dateTimeFormatter);
    this.commentContent = dto.getCommentContent();
  }
}