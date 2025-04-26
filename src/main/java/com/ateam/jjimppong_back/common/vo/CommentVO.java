package com.ateam.jjimppong_back.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.ateam.jjimppong_back.common.entity.CommentEntity;

import lombok.Getter;

@Getter
public class CommentVO {
  private Integer commentNumber;
  private String commentWriterId;
  private String commentWriteDate;
  private String commentContent;
  private Integer userLevel;

  private CommentVO(CommentEntity commentEntity) {
    this.commentNumber = commentEntity.getCommentNumber();
    this.commentWriterId = commentEntity.getUserId();
    this.commentWriteDate = commentEntity.getWriteDate();
    this.commentContent = commentEntity.getCommentContent();
  }

  public static List<CommentVO> getList(List<CommentEntity> commentEntities) {
    List<CommentVO> list = new ArrayList<>();

    for (CommentEntity commentEntity: commentEntities) {
      CommentVO vo = new CommentVO(commentEntity);
      list.add(vo);
    }

    return list;
  }
}
