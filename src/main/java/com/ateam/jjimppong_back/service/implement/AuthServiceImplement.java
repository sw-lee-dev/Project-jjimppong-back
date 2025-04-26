package com.ateam.jjimppong_back.service.implement;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ateam.jjimppong_back.common.dto.request.auth.EmailAuthCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.EmailAuthRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.IdCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.IdSearchRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.NicknameCheckRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.PasswordResetRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SignInRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SignUpRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SnsSignUpRequestDto;
import com.ateam.jjimppong_back.common.dto.request.auth.SnsUserInfoRequestDto;
import com.ateam.jjimppong_back.common.dto.response.ResponseDto;
import com.ateam.jjimppong_back.common.dto.response.auth.IdSearchResponseDto;
import com.ateam.jjimppong_back.common.dto.response.auth.IsExistingUserResponseDto;
import com.ateam.jjimppong_back.common.dto.response.auth.SignInResponseDto;
import com.ateam.jjimppong_back.common.entity.EmailAuthEntity;
import com.ateam.jjimppong_back.common.entity.SnsUserEntity;
import com.ateam.jjimppong_back.common.entity.UserEntity;
import com.ateam.jjimppong_back.common.util.EmailAuthNumberUtil;
import com.ateam.jjimppong_back.common.util.TemporaryPasswordUtil;
import com.ateam.jjimppong_back.repository.EmailAuthNumberRepository;
import com.ateam.jjimppong_back.repository.UserRepository;
import com.ateam.jjimppong_back.service.AuthService;
import com.ateam.jjimppong_back.repository.SnsUserRepository;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

import com.ateam.jjimppong_back.provider.JwtProvider;
import com.ateam.jjimppong_back.provider.MailPasswordResetProvider;
import com.ateam.jjimppong_back.provider.MailProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{

    // 중복 확인
    private final UserRepository userRepository;
    // 이메일, 인증번호 확인
    private final EmailAuthNumberRepository emailAuthNumberRepository;
    // 메일 전송
    private final MailProvider mailProvider;
    // 임시비밀번호 전송
    private final MailPasswordResetProvider mailPasswordResetProvider;
    // Jwt키 생성 및 검증
    private final JwtProvider jwtProvider;
    // 비밀번호 암호화
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final SnsUserRepository snsUserRepository;

    @Override
    public ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto) {

        try{

        String userId = dto.getUserId();
        boolean existUser = userRepository.existsByUserId(userId);
        if(existUser) return ResponseDto.existUser();

        } catch(Exception exception){ 
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> nicknameCheck(NicknameCheckRequestDto dto) {

        try{
            String userNickname = dto.getUserNickname();
            boolean existNickname = userRepository.existsByUserNickname(userNickname);
            if(existNickname) return ResponseDto.existUser();

        } catch(Exception exception){ 
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDto> emailAuth(EmailAuthRequestDto dto) {

        try{

            String userEmail = dto.getUserEmail();

            boolean existEmail = userRepository.existsByUserEmail(userEmail);
            // 이메일 중복 에러 코드 전송
            if(existEmail) return ResponseDto.duplicatiedEmail();

            // 생성한 인증번호 를 authNumber에 저장
            String authNumber = EmailAuthNumberUtil.createCodeNumber();

            EmailAuthEntity emailAuthEntity = new EmailAuthEntity(userEmail, authNumber);
            // 저장소에 이메일과, 인증번호 저장
            emailAuthNumberRepository.save(emailAuthEntity);

            // 메일 전송 기능 사용
            mailProvider.mailAuthSend(userEmail, authNumber);

        } catch(MessagingException exception){ 
            exception.printStackTrace();
            return ResponseDto.mailSendFail();
        } catch(Exception exception){ 
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ResponseDto> emailAuthId(EmailAuthRequestDto dto) {

        try{

            String userEmail = dto.getUserEmail();

            // 생성한 인증번호 를 authNumber에 저장
            String authNumber = EmailAuthNumberUtil.createCodeNumber();

            EmailAuthEntity emailAuthEntity = new EmailAuthEntity(userEmail, authNumber);
            // 저장소에 이메일과, 인증번호 저장
            emailAuthNumberRepository.save(emailAuthEntity);

            // 메일 전송 기능 사용
            mailProvider.mailAuthSend(userEmail, authNumber);

        } catch(MessagingException exception){ 
            exception.printStackTrace();
            return ResponseDto.mailSendFail();
        } catch(Exception exception){ 
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ResponseDto> emailAuthCheck(EmailAuthCheckRequestDto dto) {

        try { 

            String userEmail = dto.getUserEmail();
            String authNumber = dto.getAuthNumber();

            // 이메일과 인증번호 확인
            boolean isMatched = emailAuthNumberRepository.existsByUserEmailAndAuthNumber(userEmail, authNumber);
            // 만일 이메일과 인증번호가 일치하지 않는다면 'AF' 인증실패
            if (!isMatched) return ResponseDto.authFail();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<? super IdSearchResponseDto> idSearch(IdSearchRequestDto dto) {
        try {
            String name = dto.getName();
            String userEmail = dto.getUserEmail();
            String authNumber = dto.getAuthNumber();

            // 이메일과 인증번호가 일치하는지 확인
            boolean isMatched = emailAuthNumberRepository.existsByUserEmailAndAuthNumber(userEmail, authNumber);
            if (!isMatched) {
                return ResponseDto.authFail(); // 인증번호 불일치
            }

            // 사용자 이름과 이메일로 사용자 정보 찾기
            UserEntity userEntity = userRepository.findByNameAndUserEmail(name, userEmail);       

            // 아이디 반환
            return IdSearchResponseDto.success(userEntity.getUserId());

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<ResponseDto> passwordReset(PasswordResetRequestDto dto) {
        try {
            String userId = dto.getUserId();
            String userEmail = dto.getUserEmail();
            String authNumber = dto.getAuthNumber();

            // 이메일과 인증번호가 일치하는지 확인
            boolean isMatched = emailAuthNumberRepository.existsByUserEmailAndAuthNumber(userEmail, authNumber);
            if (!isMatched) {
                return ResponseDto.authFail(); // 인증번호 불일치
            }

            // 사용자 ID와 이메일로 사용자 정보 찾기
            UserEntity userEntity = userRepository.findByUserIdAndUserEmail(userId, userEmail);

            // 임시 비밀번호 생성
            String temporaryPassword = TemporaryPasswordUtil.createCodeNumber();

            // 비밀번호 암호화 (Spring Security 사용 시)
            String encodedPassword = passwordEncoder.encode(temporaryPassword);
            userEntity.setUserPassword(encodedPassword);

            // DB 업데이트
            userRepository.save(userEntity);

            // 이메일 전송 (임시 비밀번호 포함)
            mailPasswordResetProvider.mailPasswordSend(userEmail, temporaryPassword);
            return ResponseDto.success(HttpStatus.OK);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto) {
        
        try{
            // 이메일 인증 확인
            EmailAuthCheckRequestDto emailAuthCheckRequestDto = new EmailAuthCheckRequestDto();
            emailAuthCheckRequestDto.setUserEmail(dto.getUserEmail());
            emailAuthCheckRequestDto.setAuthNumber(dto.getAuthNumber());  // 인증번호는 사용자가 제공한 인증번호

            // 이메일 인증 확인 메서드 호출
            ResponseEntity<ResponseDto> emailAuthResponse = emailAuthCheck(emailAuthCheckRequestDto);

            // 이메일 인증 실패 시 처리
            if (emailAuthResponse.getStatusCode() != HttpStatus.OK) {
            return emailAuthResponse;  // 이메일 인증 실패 응답을 그대로 반환
            }

            String userId = dto.getUserId();
            boolean existUser = userRepository.existsByUserId(userId);
            if(existUser) return ResponseDto.existUser();

            String userNickname = dto.getUserNickname();
            boolean existNickname = userRepository.existsByUserNickname(userNickname);
            if(existNickname) return ResponseDto.existUser();

            String userEmail = dto.getUserEmail();
            boolean existEmail = userRepository.existsByUserEmail(userEmail);
            if(existEmail) return ResponseDto.existUser();

            String userPassword = dto.getUserPassword();
            // 암호화한 비밀번호
            String encodedPassword = passwordEncoder.encode(userPassword);
            dto.setUserPassword(encodedPassword);
            
            UserEntity userEntity = new UserEntity(dto);

            // // 회원가입 시 myPageEntity가 기본값으로 생성
            // MyPageEntity myPageEntity = new MyPageEntity();
            // myPageEntity.setUserId(userId);
            // myPageEntity.setUserNickname(userNickname);
            // myPageEntity.setUserLevel(1);
            // myPageEntity.setUserScore(0);
            // // 양방향 관계 설정
            // userEntity.setMyPageEntity(myPageEntity);
            // myPageEntity.setUserEntity(userEntity);

            userRepository.save(userEntity);

        } catch (Exception exception){ 
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success(HttpStatus.CREATED);

    }

    // 프론트에서 전달받은 추가 정보를 저장하는 로직 (회원가입과 동일)
    @Override
    @Transactional
    public ResponseEntity<ResponseDto> snsSignUp(SnsSignUpRequestDto dto, String snsId, String joinType) {
        try {
        // 이메일 인증 확인
        EmailAuthCheckRequestDto emailAuthCheckRequestDto = new EmailAuthCheckRequestDto();
        emailAuthCheckRequestDto.setUserEmail(dto.getUserEmail());
        emailAuthCheckRequestDto.setAuthNumber(dto.getAuthNumber());  // 인증번호는 사용자가 제공한 인증번호

        // 이메일 인증 확인 메서드 호출
        ResponseEntity<ResponseDto> emailAuthResponse = emailAuthCheck(emailAuthCheckRequestDto);

        // 이메일 인증 실패 시 처리
        if (emailAuthResponse.getStatusCode() != HttpStatus.OK) {
            return emailAuthResponse;  // 이메일 인증 실패 응답을 그대로 반환
        }

        // 사용자 ID 중복 확인
        String userId = dto.getUserId();
        boolean existUser = userRepository.existsByUserId(userId);
        if (existUser) return ResponseDto.existUser();

        // 사용자 닉네임 중복 확인
        String userNickname = dto.getUserNickname();
        boolean existNickname = userRepository.existsByUserNickname(userNickname);
        if (existNickname) return ResponseDto.existUser();

        // 사용자 이메일 중복 확인
        String userEmail = dto.getUserEmail();
        boolean existEmail = userRepository.existsByUserEmail(userEmail);
        if (existEmail) return ResponseDto.existUser();

        // 비밀번호 암호화
        String userPassword = dto.getUserPassword();
        String encodedPassword = passwordEncoder.encode(userPassword);
        dto.setUserPassword(encodedPassword);

        // 새로운 UserEntity 생성
        UserEntity userEntity = new UserEntity(dto);

        // SNS 로그인 정보 추가 (snsId, joinType)
        userEntity.setSnsId(snsId);  // SNS 로그인 ID
        userEntity.setJoinType(joinType);  // SNS 종류 (KAKAO, NAVER 등)

        // UserEntity 저장
        userRepository.save(userEntity);

        // SNS User 정보 저장
        SnsUserEntity snsUserEntity = new SnsUserEntity();
        snsUserEntity.setSnsId(snsId);
        snsUserEntity.setJoinType(joinType);  // SNS 종류
        snsUserEntity.setUserEntity(userEntity);  // UserEntity와 연결
        snsUserRepository.save(snsUserEntity);

        } catch (Exception exception) {
          exception.printStackTrace();
          return ResponseDto.databaseError();  // DB 오류 응답
        }

        return ResponseDto.success(HttpStatus.CREATED);  // 성공 응답
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
    
        String accessToken = null;

        try {

            String userId = dto.getUserId();
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.signInFail();

            String userPassword = dto.getUserPassword();
            String encodedPassword = userEntity.getUserPassword();
            boolean isMatch = passwordEncoder.matches(userPassword, encodedPassword);
            if (!isMatch) return ResponseDto.signInFail();

            accessToken = jwtProvider.create(userId);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(accessToken);

    }

    @Override
    public ResponseEntity<IsExistingUserResponseDto> isExistingUser(SnsUserInfoRequestDto dto) {
        try {
            // snsId와 joinType을 기준으로 사용자 존재 여부 확인
            Optional<UserEntity> userOpt = userRepository.findBySnsIdAndJoinType(dto.getSnsId(), dto.getJoinType());

            if (userOpt.isPresent()) {
                // 사용자가 존재하면 true 반환
                return IsExistingUserResponseDto.success(true);
            } else {
                // 사용자가 존재하지 않으면 false 반환
                return IsExistingUserResponseDto.userNotFound(false);
            }

        } catch (Exception exception) {
            // 예외 발생 시, 데이터베이스 오류 처리
            exception.printStackTrace();
            return IsExistingUserResponseDto.databaseError(false);
        }
    }
}