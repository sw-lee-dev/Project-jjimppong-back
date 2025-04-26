package com.ateam.jjimppong_back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.jjimppong_back.common.dto.request.board.PatchBoardRequestDto;
import com.ateam.jjimppong_back.common.dto.request.board.PostBoardRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetCommentResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetFilteredBoardResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetGoodResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetHateResponseDto;
import com.ateam.jjimppong_back.common.dto.response.board.GetMyBoardResponseDto;
import com.ateam.jjimppong_back.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
  
  private final BoardService boardService;

  @PostMapping({"", "/"})
  public ResponseEntity<ResponseDto> postBoard(
    @RequestBody @Valid PostBoardRequestDto requestBody,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = boardService.postBoard(requestBody, userId);
    return response;
  }

  @GetMapping("/my")
  public ResponseEntity<? super GetMyBoardResponseDto> getMyBoard(
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<? super GetMyBoardResponseDto> response = boardService.getMyBoard(userId);
    return response;
  }

  @GetMapping("/{boardNumber}")
  public ResponseEntity<? super GetBoardResponseDto> getBoard(
    @PathVariable("boardNumber") Integer boardNumber
  ){
    ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber);
    return response;
  }
  
  @PatchMapping("/{boardNumber}")
  public ResponseEntity<ResponseDto> patchBoard(
    @RequestBody @Valid PatchBoardRequestDto requestBody,
    @PathVariable("boardNumber") Integer boardInteger,
    @AuthenticationPrincipal String userId
  ){
    ResponseEntity<ResponseDto> response = boardService.patchBoard(requestBody, boardInteger, userId);
    return response;
  }

  @DeleteMapping("/{boardNumber}")
  public ResponseEntity<ResponseDto> deleteBoard(
    @PathVariable("boardNumber") Integer boardNumber,
    @AuthenticationPrincipal String userId
  ){
    ResponseEntity<ResponseDto> response = boardService.deleteBoard(boardNumber, userId);
    return response;
  }

  @GetMapping("/{boardNumber}/comment")
  public ResponseEntity<? super GetCommentResponseDto> getComment(
    @PathVariable("boardNumber") Integer boardNumber
  ) {
    ResponseEntity<? super GetCommentResponseDto> response = boardService.getComment(boardNumber);
    return response;
  }

  @GetMapping("/{boardNumber}/good")
  public ResponseEntity<? super GetGoodResponseDto> getGood(
    @PathVariable("boardNumber") Integer boardNumber
  ) {
    ResponseEntity<? super GetGoodResponseDto> response = boardService.getGood(boardNumber);
    return response;
  }

  @PutMapping("/{boardNumber}/good")
  public ResponseEntity<ResponseDto> putgood(
    @PathVariable("boardNumber") Integer boardNumber,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = boardService.putGood(boardNumber, userId);
    return response;
  }

  @GetMapping("/{boardNumber}/hate")
  public ResponseEntity<? super GetHateResponseDto> getHate(
    @PathVariable("boardNumber") Integer boardNumber
  ) {
    ResponseEntity<? super GetHateResponseDto> response = boardService.getHate(boardNumber);
    return response;
  }

  @PutMapping("/{boardNumber}/hate")
  public ResponseEntity<ResponseDto> putHate(
    @PathVariable("boardNumber") Integer boardNumber,
    @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = boardService.putHate(boardNumber, userId);
    return response;
  }

  @GetMapping("/write-date")
  public ResponseEntity<? super GetFilteredBoardResponseDto> getFilteredBoardWriteDate(
  ) {
    ResponseEntity<? super GetFilteredBoardResponseDto> response = boardService.getFilteredBoardWriteDate();
    return response;
  }

  @GetMapping("/view-count")
  public ResponseEntity<? super GetFilteredBoardResponseDto> getFilteredBoardViewCount(
  ) {
    ResponseEntity<? super GetFilteredBoardResponseDto> response = boardService.getFilteredBoardViewCount();
    return response;
  }

  @GetMapping("/good-count")
  public ResponseEntity<? super GetFilteredBoardResponseDto> getFilteredBoardGoodCount(
  ) {
    ResponseEntity<? super GetFilteredBoardResponseDto> response = boardService.getFilteredBoardGoodCount();
    return response;
  }
}
