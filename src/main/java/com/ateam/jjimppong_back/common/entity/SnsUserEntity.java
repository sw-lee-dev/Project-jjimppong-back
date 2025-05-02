package com.ateam.jjimppong_back.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "snsUser")
@Table(name = "sns_user")  // sns_user 테이블과 매핑
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnsUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autoId;

    private String snsId;
    private String joinType;

    // `userId`를 외래 키로 사용
    private String userId;

    public SnsUserEntity(String snsId, String joinType, String userId) {
        this.snsId = snsId;
        this.joinType = joinType;
        this.userId = userId;  // userId만 설정
    }
    // 새로운 생성자 추가
    public SnsUserEntity(String snsId, String joinType) {
        this.snsId = snsId;
        this.joinType = joinType;
    }
}