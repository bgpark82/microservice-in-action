package com.bgpark.user.domain.social;

import com.bgpark.user.domain.social.kakao.KakaoProfile;
import com.bgpark.user.domain.social.naver.NaverProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Slf4j
@RestController
@RequiredArgsConstructor
public class SocialController {

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/social/login/kakao")
    public ResponseEntity kakao(
            @RequestParam String accessToken,
            @RequestParam String businessName
    ) throws IOException {

        checkScopes(accessToken);
        checkTerms(accessToken);

        // 사용자 정보 조회
        log.info("accessToken={}, businessName={}", accessToken, businessName);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://kapi.kakao.com/v2/user/me");
        request.addHeader("Authorization", "Bearer " + accessToken);
        HttpResponse response = client.execute(request);
        String responseBody = "";
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            responseBody = EntityUtils.toString(response.getEntity());
            log.info("kakao profile={}", responseBody);
            KakaoProfile kakaoProfile = new ObjectMapper().readValue(responseBody, KakaoProfile.class);
            log.info("kakao profile={}", kakaoProfile);
            SocialProfile profile = SocialProfile.builder()
                    .uniqueId(kakaoProfile.getId())
                    .email(kakaoProfile.getKakaoAccount().getEmail())
                    .name(kakaoProfile.getKakaoAccount().getName())
                    .token(accessToken)
                    .build();
            log.info("social profile={}", profile);
            return ResponseEntity.ok(profile);
        }

        return ResponseEntity.ok(responseBody);
    }

    private void checkScopes(String accessToken) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://kapi.kakao.com/v2/user/scopes");
        request.addHeader("Authorization", "Bearer " + accessToken);
        HttpResponse response = client.execute(request);
        String responseBody = "";
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            responseBody = EntityUtils.toString(response.getEntity());
            log.info("scopes={}", responseBody);
        }
    }
    private void checkTerms(String accessToken) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://kapi.kakao.com/v1/user/service/terms");
        request.addHeader("Authorization", "Bearer " + accessToken);
        HttpResponse response = client.execute(request);
        String responseBody = "";
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            responseBody = EntityUtils.toString(response.getEntity());
            log.info("terms={}", responseBody);
        }
    }

    @PostMapping("/social/login/naver")
    public ResponseEntity naver(@RequestParam String accessToken) throws IOException {
        log.info("accessToken={}", accessToken);

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://openapi.naver.com/v1/nid/me");
        request.addHeader("Authorization", "Bearer " + accessToken);
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            String responseBody = EntityUtils.toString(response.getEntity());
            NaverProfile naverProfile = new ObjectMapper().readValue(responseBody, NaverProfile.class);
            log.info("naver profile={}", naverProfile);
            SocialProfile profile = SocialProfile.builder()
                    .uniqueId(naverProfile.getResponse().getId())
                    .email(naverProfile.getResponse().getEmail())
                    .name(naverProfile.getResponse().getName())
                    .token(accessToken)
                    .build();
            log.info("social profile={}", profile);
            return ResponseEntity.ok(naverProfile);
        }
        return ResponseEntity.ok().build();
    }
}
