package com.ateam.jjimppong_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.jjimppong_back.common.dto.response.board.GetRecommandBoardResponseDto;
import com.ateam.jjimppong_back.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MainPageController {

    private final BoardService boardService;

    @GetMapping({"", "/"})
    public ResponseEntity<? super GetRecommandBoardResponseDto> getrecommandBoard(
    ){
        ResponseEntity<? super GetRecommandBoardResponseDto> response = boardService.getRecommandBoard();
        return response;
    }

    
}
