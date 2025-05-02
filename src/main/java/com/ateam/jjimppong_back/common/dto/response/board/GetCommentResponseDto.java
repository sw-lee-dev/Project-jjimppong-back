package com.ateam.jjimppong_back.common.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.vo.CommentVO;

import lombok.Getter;

@Getter
public class GetCommentResponseDto extends ResponseDto {
  private List<CommentVO> comments;

  private GetCommentResponseDto(List<CommentVO> voList) {
    this.comments = voList;
  }

  public static ResponseEntity<GetCommentResponseDto> success(List<CommentVO> voList) {
    GetCommentResponseDto body = new GetCommentResponseDto(voList);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
