package com.ateam.jjimppong_back.common.dto.response.mypage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.entity.BoardEntity;

import lombok.Getter;

@Getter
public class GetDetailMyBoardResponseDto extends ResponseDto {
  private String userNickname;
  private Integer userLevel;
  private String boardContent;
  private String boardTitle;
  private String boardAddressCategory;
  private String boardDetailCategory;
  private String boardWriteDate;
  private Integer boardViewCount;
  private String boardAddress;
  private String boardImage;

  private GetDetailMyBoardResponseDto(BoardEntity boardEntity) {
    this.userNickname = boardEntity.getUserNickname();
    this.userLevel = boardEntity.getUserLevel();
    this.boardContent = boardEntity.getBoardContent();
    this.boardTitle = boardEntity.getBoardTitle();
    this.boardAddressCategory = boardEntity.getBoardAddressCategory();
    this.boardDetailCategory = boardEntity.getBoardDetailCategory();
    this.boardWriteDate = boardEntity.getBoardWriteDate();
    this.boardViewCount = boardEntity.getBoardViewCount();
    this.boardAddress = boardEntity.getBoardAddress();
    this.boardImage = boardEntity.getBoardImage();
  }

  public static ResponseEntity<GetDetailMyBoardResponseDto> success(BoardEntity boardEntity) {
    GetDetailMyBoardResponseDto body = new GetDetailMyBoardResponseDto(boardEntity);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
