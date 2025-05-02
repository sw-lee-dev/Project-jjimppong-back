package com.ateam.jjimppong_back.common.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.entity.BoardEntity;

import lombok.Getter;

@Getter
public class GetBoardResponseDto extends ResponseDto {
  private String userId;
  private String userNickname;
  private Integer userLevel;
  private String boardContent;
  private String boardTitle;
  private String boardAddressCategory;
  private List<String> boardDetailCategory;
  private String boardWriteDate;
  private Integer boardViewCount;
  private String boardAddress;
  private String boardImage;
  private String textFileUrl;

  private GetBoardResponseDto(BoardEntity boardEntity){
    this.userId = boardEntity.getUserId();
    this.userNickname = boardEntity.getUserNickname();
    this.userLevel = boardEntity.getUserLevel();
    this.boardContent = boardEntity.getBoardContent();
    this.boardTitle = boardEntity.getBoardTitle();
    this.boardAddressCategory = boardEntity.getBoardAddressCategory();
    this.boardWriteDate = boardEntity.getBoardWriteDate();
    this.boardViewCount = boardEntity.getBoardViewCount();
    this.boardAddress = boardEntity.getBoardAddress();
    this.boardImage = boardEntity.getBoardImage();
    this.textFileUrl = boardEntity.getTextFileUrl();
  }

  public static ResponseEntity<GetBoardResponseDto> success(BoardEntity boardEntity) {
    GetBoardResponseDto body = new GetBoardResponseDto(boardEntity);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
