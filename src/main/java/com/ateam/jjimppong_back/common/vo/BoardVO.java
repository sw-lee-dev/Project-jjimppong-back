package com.ateam.jjimppong_back.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.ateam.jjimppong_back.common.entity.BoardEntity;

import lombok.Getter;

@Getter
public class BoardVO {
  private Integer boardNumber;
  private String userId;
  private String userNickname;
  private Integer userLevel;
  private String boardWriteDate;
  private String boardAddressCategory;
  private String boardDetailCategory;
  private String boardTitle;
  private String boardContent;
  private Integer boardViewCount;
  private Integer boardScore;
  private String boardAddress;
  private String boardImage;

  private BoardVO(BoardEntity boardEntity) {
    this.boardNumber = boardEntity.getBoardNumber();
    this.boardWriteDate = boardEntity.getBoardWriteDate();
    this.boardAddressCategory = boardEntity.getBoardAddressCategory();
    this.boardDetailCategory = boardEntity.getBoardDetailCategory();
    this.boardTitle = boardEntity.getBoardTitle();
    this.boardContent = boardEntity.getBoardContent();
    this.boardAddress = boardEntity.getBoardAddress();
    this.boardViewCount = boardEntity.getBoardViewCount();
    this.boardImage = boardEntity.getBoardImage();
  }

  public BoardVO (
  Integer boardNumber, String boardContent, String boardTitle,
  String boardAddressCategory, String boardDetailCategory, String boardWriteDate,
  Integer boardViewCount, Integer boardScore, String boardAddress, String boardImage,
  String userId, String userNickname, Integer userLevel) {
    this.boardNumber = boardNumber;
    this.boardContent = boardContent;
    this.boardTitle = boardTitle;
    this.boardAddressCategory = boardAddressCategory;
    this.boardDetailCategory = boardDetailCategory;
    this.boardWriteDate = boardWriteDate;
    this.boardViewCount = boardViewCount;
    this.boardScore = boardScore;
    this.boardAddress = boardAddress;
    this.boardImage = boardImage;
    this.userId = userId;
    this.userNickname = userNickname;
    this.userLevel = userLevel;
  }

  public static List<BoardVO> getList(List<BoardEntity> boardEntities) {

    List<BoardVO> list = new ArrayList<>();
    for (BoardEntity boardEntity: boardEntities) {
      BoardVO vo = new BoardVO(boardEntity);
      list.add(vo);
    }

    return list;
  }
}
