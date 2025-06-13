package com.ateam.jjimppong_back.common.entity;

import com.ateam.jjimppong_back.common.util.UserLevelUtil;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "myScore")
@Table(name = "my_score")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyScoreEntity {
  @Id
  private String userId;
  private String userNickname;
  private Integer userLevel;
  private Integer userScore;

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserEntity userEntity;

  public UserLevelUtil getLevel() {
    return UserLevelUtil.getUserLevel(this.userScore);
  }

}
