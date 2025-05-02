package com.ateam.jjimppong_back.common.entity;

import com.ateam.jjimppong_back.common.dto.request.auth.SignUpRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SnsSignUpRequestDto;
import com.ateam.jjimppong_back.common.dto.request.mypage.PatchSignInUserRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private String userId;
    private String userNickname;
    private String userPassword;
    private String userEmail;
    private String joinType;
    private String name;
    private String address;
    private String detailAddress;
    private Integer userLevel;
    private String gender;
    private String profileImage;
    private String snsId;

    // userEntity와 myPageEntity간의 일대일 연관관계 매핑
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private MyPageEntity myPageEntity;

    public UserEntity(SignUpRequestDto dto){ 
        this.userId = dto.getUserId();
        this.userNickname = dto.getUserNickname();
        this.userEmail = dto.getUserEmail();
        this.name = dto.getName();
        this.userPassword = dto.getUserPassword();
        this.address = dto.getAddress();
        this.detailAddress = dto.getDetailAddress();
        this.profileImage = dto.getProfileImage();
        this.gender = dto.getGender();
        this.userLevel = Integer.parseInt(dto.getUserLevel());
        this.joinType = dto.getJoinType();
    }

    public UserEntity(SnsSignUpRequestDto dto, String userId){ 
        this.userId = userId;
        this.userNickname = dto.getUserNickname();
        this.userEmail = dto.getUserEmail();
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.detailAddress = dto.getDetailAddress();
        this.profileImage = dto.getProfileImage();
        this.gender = dto.getGender();
        this.userLevel = Integer.parseInt(dto.getUserLevel());
        this.joinType = dto.getJoinType();
        this.snsId = dto.getSnsId();
    }

    public void patch(PatchSignInUserRequestDto dto) {
        this.userNickname = dto.getUserNickname();
        this.userPassword = dto.getUserPassword();
        this.address = dto.getAddress();
        this.detailAddress = dto.getDetailAddress();
        this.profileImage = dto.getProfileImage();
    }
}
