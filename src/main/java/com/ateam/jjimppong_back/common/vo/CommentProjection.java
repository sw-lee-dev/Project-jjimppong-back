package com.ateam.jjimppong_back.common.vo;

public interface CommentProjection {
    Integer getCommentNumber();
    String getCommentWriteDate();
    String getCommentContent();
    String getCommentWriterId();
    String getUserNickname();
    Integer getUserLevel();
}
