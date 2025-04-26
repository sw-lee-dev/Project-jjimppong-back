package com.ateam.jjimppong_back.common.vo;

public interface BoardProjection {
  Integer getBoardNumber();
  String getBoardContent();
  String getBoardTitle();
  String getBoardAddressCategory();
  String getBoardDetailCategory();
  String getBoardWriteDate();
  Integer getBoardViewCount();
  Integer getBoardScore();
  String getBoardAddress();
  String getBoardImage();
  String getUserId();
  String getUserNickname();
  Integer setUserLevel(Integer userLevel);
}
