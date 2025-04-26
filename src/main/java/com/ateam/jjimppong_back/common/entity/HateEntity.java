package com.ateam.jjimppong_back.common.entity;

import com.ateam.jjimppong_back.common.entity.pk.HatePK;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "hate")
@Table(name = "hate")
@IdClass(HatePK.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HateEntity {
  @Id
  private String userId;
  @Id
  private Integer boardNumber;
}
