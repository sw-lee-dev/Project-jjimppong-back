package com.ateam.jjimppong_back.service.implement;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ateam.jjimppong_back.common.entity.CustomOAuth2User;
import com.ateam.jjimppong_back.common.entity.UserEntity;
import com.ateam.jjimppong_back.provider.JwtProvider;
import com.ateam.jjimppong_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService{
    
    private final UserRepository userRepository;
    // 토큰
    private final JwtProvider jwtProvider;

    @Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // toUpperCase() : 모두 대문자로 변경
        String registration = userRequest.getClientRegistration().getClientName().toUpperCase();

        String snsId = getSnsId(oAuth2User, registration);
        UserEntity userEntity = userRepository.findByJoinTypeAndSnsId(registration, snsId);

        CustomOAuth2User customOAuth2User = null;

        Map<String, Object> attributes = new HashMap<>();

        if(userEntity == null){ 
            // 회원가입 페이지로 넘긴다. (회원가입 안되어있을시)
            attributes.put("snsId", snsId);
            attributes.put("joinType", registration);
            // 3번째는 회원가입 여부 / false = 회원가입 안함
            customOAuth2User = new CustomOAuth2User(snsId, attributes, false);
            
        } else { 
            // 회원가입이 되어있을때
            String userId = userEntity.getUserId();
            String accessToken = jwtProvider.create(userId);
            attributes.put("accessToken", accessToken);

            // 3번째는 회원가입 여부
            customOAuth2User = new CustomOAuth2User(userId, attributes, true);
        }

        return customOAuth2User;

    }

    // function: 결과로 받은 유저 정보에서 ragistration에 따라 id 값을 추출하는 함수 //
    private String getSnsId(OAuth2User oAuth2User, String registration){

        String snsId = null;

        // 대, 소문자 구분없게 수정
        if("KAKAO".equalsIgnoreCase(registration)){ 
            snsId = oAuth2User.getName();
        }
        if ("NAVER".equalsIgnoreCase(registration)) {
            Map<String, String> response = (Map<String, String>) oAuth2User.getAttributes().get("response");
            snsId = response.get("id");
          }

        return snsId;
    }
}
