package com.ateam.jjimppong_back.common.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.vo.FilteredBoardVO;

import lombok.Getter;



@Getter
public class GetFilteredBoardResponseDto extends ResponseDto {
    private List<FilteredBoardVO> boards;
    
    private GetFilteredBoardResponseDto(List<FilteredBoardVO> voList){
        this.boards = voList;
    }

    public static ResponseEntity<GetFilteredBoardResponseDto> success(List<FilteredBoardVO> voList) {
        GetFilteredBoardResponseDto body = new GetFilteredBoardResponseDto(voList);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
