package com.ateam.jjimppong_back.service.implement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ateam.jjimppong_back.common.entity.SnsUserEntity;
import com.ateam.jjimppong_back.common.entity.CustomOAuth2User;
import com.ateam.jjimppong_back.common.entity.UserEntity;
import com.ateam.jjimppong_back.provider.JwtProvider;
import com.ateam.jjimppong_back.repository.SnsUserRepository;
import com.ateam.jjimppong_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final SnsUserRepository snsUserRepository;
    private final JwtProvider jwtProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registration = userRequest.getClientRegistration().getClientName().toUpperCase(); // joinType (e.g., KAKAO)
        String snsId = getSnsId(oAuth2User, registration);  // SNS ID 추출

        Map<String, Object> attributes = new HashMap<>();
        CustomOAuth2User customOAuth2User;

        // SNS 로그인 정보 조회
        Optional<SnsUserEntity> optionalSnsUser = snsUserRepository.findBySnsIdAndJoinType(snsId, registration);

        if (optionalSnsUser.isEmpty()) {
            // SNS 사용자 정보가 DB에 없는 경우 → 회원가입이 필요함
            attributes.put("snsId", snsId);
            attributes.put("joinType", registration);
            customOAuth2User = new CustomOAuth2User(snsId, attributes, false); // 회원가입 필요
        } else {
            SnsUserEntity snsUser = optionalSnsUser.get();
            UserEntity userEntity = snsUser.getUserEntity();

            if (userEntity == null) {
                // 아직 회원가입 안된 경우 -> 추가정보 입력
                attributes.put("snsId", snsId);
                attributes.put("joinType", registration);
                customOAuth2User = new CustomOAuth2User(snsId, attributes, false);
            } else {
                // 기존 유저로 로그인 처리
                String userId = userEntity.getUserId();
                String accessToken = jwtProvider.create(userId);
                attributes.put("accessToken", accessToken);
                customOAuth2User = new CustomOAuth2User(userId, attributes, true); // 로그인 완료
            }
        }

        return customOAuth2User;
    }

    // SNS에서 받은 ID 추출 및 DB 저장
    private String getSnsId(OAuth2User oAuth2User, String registration) {
        String snsId = null;
        String joinType = null;

        // SNS 종류에 따라 처리
        if (registration.equals("KAKAO")) {
            snsId = oAuth2User.getName();  // KAKAO는 기본적으로 getName()으로 ID 제공
            joinType = "KAKAO";
        } else if (registration.equals("NAVER")) {
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            snsId = (String) response.get("id");  // NAVER는 response.id로 ID 제공
            joinType = "NAVER";
        }

        // SnsUserEntity 객체 생성
        SnsUserEntity snsUserEntity = new SnsUserEntity();
        snsUserEntity.setSnsId(snsId);
        snsUserEntity.setJoinType(joinType);

        // snsUserEntity 객체를 데이터베이스에 저장
        snsUserRepository.save(snsUserEntity);

        return snsId;  // snsId 반환
    }
}