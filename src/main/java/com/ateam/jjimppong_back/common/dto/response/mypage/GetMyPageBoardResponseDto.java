package com.ateam.jjimppong_back.common.dto.response.mypage;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.entity.BoardEntity;
import com.ateam.jjimppong_back.common.vo.MyPageBoardVO;

import lombok.Getter;

@Getter
public class GetMyPageBoardResponseDto extends ResponseDto {

  List<MyPageBoardVO> myBoards;

  private GetMyPageBoardResponseDto(List<BoardEntity> boardEntities) {
    this.myBoards = MyPageBoardVO.getList(boardEntities);
  }

  public static ResponseEntity<GetMyPageBoardResponseDto> success (List<BoardEntity> boardEntities) {
    GetMyPageBoardResponseDto body = new GetMyPageBoardResponseDto(boardEntities);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
