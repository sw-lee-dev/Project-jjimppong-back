package com.ateam.jjimppong_back.common.entity;


import java.sql.Timestamp;

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
    @Column(name = "sns_id", nullable = false, length = 100)
    private String snsId;

    @Column(name = "join_type", nullable = false, length = 6)
    private String joinType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private UserEntity userEntity;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public SnsUserEntity(String snsId, String joinType) {
        this.snsId = snsId;
        this.joinType = joinType;
    }

    @PrePersist
    public void onCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }  
}
