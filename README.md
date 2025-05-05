## 🔴 프로젝트 개요
### Jjimppong Back
코리아아이티아카데미 부산  
**[산대특] 빅데이터 활용 실버케어테크 인지치료소프트웨어 개발 과정**  
Back-end 프로젝트  

## 🔵 REST API 명세서
### 링크
배포 링크  

## 🟢 주요 기능
- 회원가입, 로그인
- 카카오, 네이버 로그인
- 마이페이지
- 사용자 점수로 회원 등급 표시
- 게시글 작성, 수정, 삭제, 보기
- 게시글 조회수, 찜, 싫어요, 댓글 작성, 댓글 삭제
- 네이버 지도 API를 활용한 지역 정보 및 길찾기 기능

## 🟡 기술 스택
- Spring boot 3.4.3
- Java 21
- Lombok
- Spring web
- Spring webflux
- Spring mail
- Spring validation
- Spring data JPA
- Spring security
- Spring oauth2 client
- JJWT 0.11.2
- MySQL Connector

## 🟣 프로젝트 실행 방법
### 클론 및 폴더 이동
```bash
git clone 깃주소
cd 프로젝트명
```

### 빌드 방법
```bash
./gradlew clean build
```

## 📂 폴더 구조
```md
📂 PROJECT-BACK
├ 📂 node_modules
│ ├ 📂 cors
│ ├ 📂 object-assign
│ └ 📂 vary
├ 📂 src
│ └ 📂 java
│   └ 📂 com
│     └ 📂 ateam
│       └ 📂 jjimppong_back
│         ├ 📂 common
│         │ ├ 📂 dto
│         │ │ ├ 📂 request
│         │ │ └ 📂 response
│         │ ├ 📂 entity
│         │ │ └ 📂 pk
│         │ ├ 📂 util
│         │ └ 📂 vo
│         ├ 📂 config
│         ├ 📂 controller
│         ├ 📂 filter
│         ├ 📂 handler
│         ├ 📂 provider
│         ├ 📂 repository
│         └ 📂 service
│           └ 📂 implememt
├ 📂 gradle
├ 📄 build.gradle
└ 💻 gradlew
```

## 📑 라이센스
### Copyright (c) <2025.04> <Ateam>