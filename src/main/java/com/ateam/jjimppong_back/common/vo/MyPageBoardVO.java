package com.ateam.jjimppong_back.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.ateam.jjimppong_back.common.entity.BoardEntity;

import lombok.Getter;

@Getter
public class MyPageBoardVO {
  private Integer boardNumber;
  private String boardImage;
  private String boardTitle;
  private String boardWriteDate;
  private Integer boardViewCount;

  private MyPageBoardVO(BoardEntity boardEntity) {
    this.boardNumber = boardEntity.getBoardNumber();
    this.boardImage = boardEntity.getBoardImage();
    this.boardTitle = boardEntity.getBoardTitle();
    this.boardWriteDate = boardEntity.getBoardWriteDate();
    this.boardViewCount = boardEntity.getBoardViewCount();
  }

  public static List<MyPageBoardVO> getList(List<BoardEntity> boardEntities) {

    List<MyPageBoardVO> list = new ArrayList<>();
    for (BoardEntity boardEntity: boardEntities) {
      MyPageBoardVO vo = new MyPageBoardVO(boardEntity);
      list.add(vo);
    }

    return list;
  }
}
