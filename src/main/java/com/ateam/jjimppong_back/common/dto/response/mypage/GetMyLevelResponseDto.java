package com.ateam.jjimppong_back.common.dto.response.mypage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.entity.MyScoreEntity;
import com.ateam.jjimppong_back.common.util.UserLevelUtil;

import lombok.Getter;

@Getter
public class GetMyLevelResponseDto extends ResponseDto {
  private Integer userLevel;
  private Integer userScore;

  private GetMyLevelResponseDto(MyScoreEntity myScoreEntity) {
    UserLevelUtil u = myScoreEntity.getLevel();
    this.userLevel = u.getNumericValue();
    this.userScore = myScoreEntity.getUserScore();
  }

  public static ResponseEntity<GetMyLevelResponseDto> success (MyScoreEntity myScoreEntity) {
    GetMyLevelResponseDto body = new GetMyLevelResponseDto(myScoreEntity);
    return ResponseEntity.status(HttpStatus.OK).body(body);
  }
}
