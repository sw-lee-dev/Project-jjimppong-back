package com.ateam.jjimppong_back.common.vo;

import java.util.ArrayList;
import java.util.List;

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

  public static List<BoardVO> getList(List<BoardProjection> projections) {
    List<BoardVO> list = new ArrayList<>();
    for (BoardProjection p: projections) {
      BoardVO vo = new BoardVO(
        p.getBoardNumber(),
        p.getBoardContent(),
        p.getBoardTitle(),
        p.getBoardAddressCategory(),
        p.getBoardDetailCategory(),
        p.getBoardWriteDate(),
        p.getBoardScore(),
        p.getBoardViewCount(),
        p.getBoardAddress(),
        p.getBoardImage(),
        p.getUserId(),
        p.getUserNickname(),
        p.getUserLevel()
      );
      list.add(vo);
    }

    return list;
  }
}
