package com.ateam.jjimppong_back.common.vo;

import java.util.ArrayList;
import java.util.List;



import lombok.Getter;

@Getter
public class FilteredBoardVO {
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
    private Integer commentCount;
    private Integer userLevel;

    public FilteredBoardVO(Integer boardNumber, String boardWriteDate, String boardAddressCategory, String boardDetailCategory,
    String boardTitle, Integer boardViewCount, Integer boardScore, String boardImage, String userNickname, Integer userLevel,
    Integer goodCount, Integer commentCount 
    ){
        this.boardNumber = boardNumber;
        this.boardWriteDate = boardWriteDate;
        this.boardAddressCategory = boardAddressCategory;
        this.boardDetailCategory = boardDetailCategory;
        this.boardTitle = boardTitle;
        this.boardViewCount = boardViewCount;
        this.boardScore = boardScore;
        this.boardImage = boardImage;
        this.userNickname = userNickname;
        this.userLevel = userLevel;
        this.goodCount = goodCount;
        this.commentCount = commentCount;
    }

    public static List<FilteredBoardVO> getList(List<FilteredBoardProjection> projections) {
        List<FilteredBoardVO> list = new ArrayList<>();
        for (FilteredBoardProjection p : projections) {
            FilteredBoardVO vo = new FilteredBoardVO(
                p.getBoardNumber(),
                p.getBoardWriteDate(),
                p.getBoardAddressCategory(),
                p.getBoardDetailCategory(),
                p.getBoardTitle(),
                p.getBoardViewCount(),
                p.getBoardScore(),
                p.getBoardImage(),
                p.getUserNickname(),
                p.getUserLevel(),
                p.getGoodCount(),
                p.getCommentCount()
            );
            list.add(vo);
        }
        return list;
    }
    
}
