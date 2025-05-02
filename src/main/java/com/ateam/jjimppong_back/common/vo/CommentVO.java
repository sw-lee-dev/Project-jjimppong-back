package com.ateam.jjimppong_back.common.vo;


import lombok.Getter;

@Getter
public class CommentVO {
  private Integer commentNumber;
  private String commentWriteDate;
  private String commentContent;
  private String commentWriterId;
  private String userNickname;
  private Integer userLevel;

  public CommentVO(Integer commentNumber, String commentWriteDate, String commentContent, String commentWriterId, String userNickname, Integer userLevel){
    this.commentNumber = commentNumber;
    this.commentWriteDate = commentWriteDate;
    this.commentContent = commentContent;
    this.commentWriterId = commentWriterId;
    this.userNickname = userNickname;
    this.userLevel = userLevel;
  }
}
