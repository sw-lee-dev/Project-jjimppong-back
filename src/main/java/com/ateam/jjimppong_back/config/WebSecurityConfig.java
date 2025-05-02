package com.ateam.jjimppong_back.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ateam.jjimppong_back.filter.JwtAuthenticationFilter;
import com.ateam.jjimppong_back.handler.OAuth2SuccessHandler;
import com.ateam.jjimppong_back.service.implement.OAuth2UserServiceImplement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// class: Spring Web 보안 설정 //
@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
  
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final OAuth2UserServiceImplement oAuth2UserService;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;

  // function: Web Security 설정 메소드 //
  @Bean
  protected SecurityFilterChain configure(HttpSecurity security) throws Exception {

    security
    // description: Basic 인증 미사용 지정 //
    // description: 웹 페이지 들어갔을때 로그인 페이지 미사용 //
    .httpBasic(HttpBasicConfigurer::disable)
    // description: Session 유지하지 않음 지정 //
    .sessionManagement(management -> management
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    )
    // description: csrf 취약점 대비 미사용 지정 //
    .csrf(CsrfConfigurer::disable)
    // description: CORS 정책 설정 //
    .cors(cors -> cors.configurationSource(corsConfigurationSource()))

    // description: 인가 설정 //
    .authorizeHttpRequests(request -> request
        .requestMatchers("/api/v1/auth", "/api/v1/auth/**", "/oauth2/**").permitAll()
        .requestMatchers("/file/**", "/api/v1/main", "/api/v1/main/**").permitAll()
        .requestMatchers("/api/v1/festivals/**", "/api/festivals/**", "/popup-stores/**", "/restaurants/**").permitAll()
        .requestMatchers("/api/v1/auth/password-reset").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/board", "/api/v1/board/**").permitAll() // GET 요청으로 제대로 나눠주지 않으면 POST때 엉뚱한 토큰을 받아도 그냥 허용하게된다. 주의.
        .requestMatchers(HttpMethod.PUT, "/api/v1/board/view-count","/api/v1/board/view-count/**").permitAll() // 조회수 올리는 데에 로그인+비로그인 모든 사용자 허용
        .anyRequest().authenticated()
    )
    // description: Oauth 로그인 적용 //
    .oauth2Login(oauth2 -> oauth2
        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
        // 들어오는 곳
        .authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/auth/sns"))
        .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
        .successHandler(oAuth2SuccessHandler)
    )

    // description: 인증 또는 인가 실패에 대한 처리 //
    .exceptionHandling(exception -> exception
        .authenticationEntryPoint(new AuthenticationFailEntryPoint())
    )

    // description: Jwt Authentication Filter 등록 //
    // Jwt 필터가 인증 없이 접근할 수 있는 경로에 대해서는 필터를 적용하지 않도록 설정 //
    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
    .exceptionHandling(exception -> exception
        .authenticationEntryPoint(new AuthenticationFailEntryPoint())
    );

  return security.build();
}

  // function: CORS 정책 설정 객체를 반환하는 메소드 //
  @Bean
  protected CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedOrigin("http://127.0.0.1:3000"); // React 개발 서버 도메인
    configuration.addAllowedOrigin("http://localhost:3000"); // 추가 허용
    configuration.setAllowCredentials(true);

    configuration.addExposedHeader("Authorization");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

}

class AuthenticationFailEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    
    authException.printStackTrace();

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write("{ \"code\": \"AF\", \"message\": \"Auth Fail.\" }");

  }
  
}
