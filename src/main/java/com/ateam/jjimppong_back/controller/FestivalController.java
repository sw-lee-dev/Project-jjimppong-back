package com.ateam.jjimppong_back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.ateam.jjimppong_back.common.dto.response.FestivalDTO;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api")
public class FestivalController {   
    
    @Value("${tourapi.service-key}")
    private String serviceKey;

    @Value("${jwt.secret}")
    private String secretKey;

    private final WebClient webClient = WebClient.create();

    @GetMapping("/festivals")
    public List<FestivalDTO> getFestivals(@RequestParam String areaCode, @RequestParam String sigunguCode, @RequestHeader(value = "Authoriztion", required = false) String authHeader) { 

        // TODO: 로그인 인증 토큰 검증 시 활성화
        /* 
        String token = authHeader.replace("Bearer ", "");
        validateToken(token); 
        */

        // 인증 상관없이 축제 API 출력
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            validateToken(token);
        }

        String apiUrl = "https://apis.data.go.kr/B551011/KorService1/searchFestival1";

        String requestUrl = apiUrl + 
                "?serviceKey=" + serviceKey +
                "&numOfRows=5" +
                "&pageNo=1" +
                "&MobileOS=ETC" +
                "&MobileApp=MapApp" +
                "&eventStartDate=2025-05-01" +
                "&areaCode=" + areaCode +
                "&sigunguCode=" + sigunguCode +
                "&_type=json";

        return webClient.get()
            .uri(URI.create(requestUrl))
            .retrieve()
            .bodyToMono(String.class)
            .map(this::parseFestivalDTOs)
            .block();
}

// 인증 필요시
private void validateToken(String token) {
    try {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
    } catch (Exception e) {
        throw new RuntimeException("유효하지 않은 토큰입니다.");
    }
}



private List<FestivalDTO> parseFestivalDTOs(String responseBody) {
    List<FestivalDTO> festivalDTOList = new ArrayList<>();

    try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode festivalItem = rootNode.path("response").path("body").path("items").path("item");

        // 축제 기간 해당 월 설정
        String currentMonth = String.format("%02d", java.time.LocalDate.now().getMonthValue());

        if (festivalItem.isArray()) {
            for (JsonNode festivalNode : festivalItem) {
                String title = festivalNode.path("title").asText();
                String startDate = festivalNode.path("eventstartdate").asText();
                String endDate = festivalNode.path("eventenddate").asText();
                String image = festivalNode.path("firstimage").asText();
                String address = festivalNode.path("addr1").asText();

                // 축제 기간 해당 월 설정
                if (startDate != null && startDate.length() == 8) {
                    String startMonth = startDate.substring(4, 6);
                    
                    if (startMonth.equals(currentMonth)) {
                        FestivalDTO festivalDTO = new FestivalDTO(title, startDate, endDate, image, address);
                        festivalDTOList.add(festivalDTO);
                    }
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return festivalDTOList;
}
}