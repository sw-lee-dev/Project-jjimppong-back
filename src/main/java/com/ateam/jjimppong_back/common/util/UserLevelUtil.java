package com.ateam.jjimppong_back.common.util;

// 계정 점수에 따른 사용자 레벨 표시를 위한 enum 클래스 //
public enum UserLevelUtil {
  A(5),
  B(4),
  C(3),
  D(2),
  E(1);

  private final Integer numericValue;

  UserLevelUtil(Integer numericValue) {
    this.numericValue = numericValue;
  }

  public Integer getNumericValue() {
    return numericValue;
  }

  public static UserLevelUtil getUserLevel(Integer userScore) {
    if (userScore >= 400) return A;
    else if (userScore >= 300) return B;
    else if (userScore >= 200) return C;
    else if (userScore >= 100) return D;
    else return E;
  }
}
