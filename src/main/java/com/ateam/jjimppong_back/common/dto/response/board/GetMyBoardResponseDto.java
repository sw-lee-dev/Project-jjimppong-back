package com.ateam.jjimppong_back.common.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.vo.BoardVO;

import lombok.Getter;

@Getter
public class GetMyBoardResponseDto extends ResponseDto {

  private List<BoardVO> boards;

  private GetMyBoardResponseDto(List<BoardVO> voList) {
    this.boards = voList;
  }

  // private GetMyBoardResponseDto(List<BoardEntity> boardEntities) {
  //   this.boards = BoardVO.getList(boardEntities);
  // }

  public static ResponseEntity<GetMyBoardResponseDto> success (List<BoardVO> voList) {
    GetMyBoardResponseDto body = new GetMyBoardResponseDto(voList);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }

  // public static ResponseEntity<GetMyBoardResponseDto> success (List<BoardEntity> boardEntities) {
  //   GetMyBoardResponseDto body = new GetMyBoardResponseDto(boardEntities);
  //   return ResponseEntity.status(HttpStatus.OK).body(body);
  // }
  
}
