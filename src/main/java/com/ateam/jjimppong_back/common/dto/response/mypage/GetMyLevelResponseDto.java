package com.ateam.jjimppong_back.common.dto.response.mypage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.entity.MyPageEntity;
import com.ateam.jjimppong_back.common.util.UserLevelUtil;

import lombok.Getter;

@Getter
public class GetMyLevelResponseDto extends ResponseDto {
  private Integer userLevel;
  private Integer userScore;

  private GetMyLevelResponseDto(MyPageEntity myPageEntity) {
    UserLevelUtil u = myPageEntity.getLevel();
    this.userLevel = u.getNumericValue();
    this.userScore = myPageEntity.getUserScore();
  }

  public static ResponseEntity<GetMyLevelResponseDto> success (MyPageEntity myPageEntity) {
    GetMyLevelResponseDto body = new GetMyLevelResponseDto(myPageEntity);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
