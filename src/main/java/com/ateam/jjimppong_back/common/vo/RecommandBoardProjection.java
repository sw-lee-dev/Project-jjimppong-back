package com.ateam.jjimppong_back.common.vo;

public interface RecommandBoardProjection {
    Integer getBoardNumber();
    String getBoardWriteDate();
    String getBoardAddressCategory();
    String getBoardDetailCategory();
    String getBoardTitle();
    Integer getBoardViewCount();
    Integer getBoardScore();
    String getBoardImage();
    String getUserNickname();
    Integer getGoodCount();
}
