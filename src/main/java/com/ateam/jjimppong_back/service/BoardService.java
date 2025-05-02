package com.ateam.jjimppong_back.service;

import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.request.board.PatchBoardRequestDto;
import com.ateam.jjimppong_back.common.dto.request.board.PostBoardRequestDto;
import com.ateam.jjimppong_back.common.dto.request.board.PostCommentRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetCommentResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetFilteredBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetGoodResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetHateResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetRecommandBoardResponseDto;

public interface BoardService {
  ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto, String userId);
  
  // ResponseEntity<? super GetMyBoardResponseDto> getMyBoard(String userId);

  ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
  ResponseEntity<? super GetRecommandBoardResponseDto> getRecommandBoard();
  ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String userId);
  ResponseEntity<ResponseDto> deleteBoard(Integer boardNumber, String userId);

  ResponseEntity<? super GetGoodResponseDto> getGood(Integer boardNumber);
  ResponseEntity<ResponseDto> putGood(Integer boardNumber, String userId);

  ResponseEntity<? super GetHateResponseDto> getHate(Integer boardNumber);
  ResponseEntity<ResponseDto> putHate(Integer boardNumber, String userId);

  ResponseEntity<ResponseDto> putViewCount(Integer boardNumber);

  ResponseEntity<ResponseDto> putBoardScore(Integer boardNumber);

  ResponseEntity<? super GetCommentResponseDto> getComment(Integer boardNumber);
  ResponseEntity<ResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String userId);
  ResponseEntity<ResponseDto> deleteComment(Integer commentNumber, String userId);

  ResponseEntity<? super GetFilteredBoardResponseDto> getFilteredBoardWriteDate();
  ResponseEntity<? super GetFilteredBoardResponseDto> getFilteredBoardViewCount();
  ResponseEntity<? super GetFilteredBoardResponseDto> getFilteredBoardGoodCount();
}
