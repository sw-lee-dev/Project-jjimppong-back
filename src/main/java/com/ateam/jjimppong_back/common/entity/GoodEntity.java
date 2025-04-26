package com.ateam.jjimppong_back.common.entity;

import com.ateam.jjimppong_back.common.entity.pk.GoodPK;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "good")
@Table(name = "good")
@IdClass(GoodPK.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodEntity {
  @Id
  private String userId;
  @Id
  private Integer boardNumber;
}
