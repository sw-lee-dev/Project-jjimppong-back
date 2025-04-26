package com.ateam.jjimppong_back.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.ateam.jjimppong_back.common.entity.BoardEntity;

import lombok.Getter;

@Getter
public class RecommandBoardVO {
    private Integer boardNumber;
    private String boardWriteDate;
    private String boardAddressCategory;
    private String boardDetailCategory;
    private String boardTitle;
    private Integer boardViewCount;
    private Integer boardScore;
    private String boardImage;
    private String userNickname;
    private Integer goodCount;

  //   private RecommandBoardVO(BoardEntity boardEntity) {
  //   this.boardWriteDate = boardEntity.getBoardWriteDate();
  //   this.boardAddressCategory = boardEntity.getBoardAddressCategory();
  //   this.boardDetailCategory = boardEntity.getBoardDetailCategory();
  //   this.boardTitle = boardEntity.getBoardTitle();
  //   this.boardViewCount = boardEntity.getBoardViewCount();
  //   this.boardScore = boardViewCount;
  //   this.boardImage = boardEntity.getBoardImage();
  // }

    public RecommandBoardVO(Integer boardNumber, String boardWriteDate, String boardAddressCategory, String boardDetailCategory,
                            String boardTitle, Integer boardViewCount, Integer boardScore,
                            String boardImage, String userNickname, Integer goodCount) {
        this.boardNumber = boardNumber;
        this.boardWriteDate = boardWriteDate;
        this.boardAddressCategory = boardAddressCategory;
        this.boardDetailCategory = boardDetailCategory;
        this.boardTitle = boardTitle;
        this.boardViewCount = boardViewCount;
        this.boardScore = boardScore;
        this.boardImage = boardImage;
        this.userNickname = userNickname;
        this.goodCount = goodCount;
    }

    public static List<RecommandBoardVO> getList(List<RecommandBoardProjection> projections) {
        List<RecommandBoardVO> list = new ArrayList<>();
        for (RecommandBoardProjection p : projections) {
            RecommandBoardVO vo = new RecommandBoardVO(
                p.getBoardNumber(),
                p.getBoardWriteDate(),
                p.getBoardAddressCategory(),
                p.getBoardDetailCategory(),
                p.getBoardTitle(),
                p.getBoardViewCount(),
                p.getBoardScore(),
                p.getBoardImage(),
                p.getUserNickname(),
                p.getGoodCount()  // goodCount로 사용
            );
            list.add(vo);
        }
        return list;
    }
}
