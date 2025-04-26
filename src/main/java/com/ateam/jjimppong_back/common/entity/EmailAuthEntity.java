package com.ateam.jjimppong_back.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "emailAuth")
@Table(name = "email_auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuthEntity {
  @Id
  private String userEmail;
  private String authNumber;
}