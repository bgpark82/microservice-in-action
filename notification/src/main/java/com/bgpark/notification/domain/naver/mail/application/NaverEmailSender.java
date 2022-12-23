package com.bgpark.notification.domain.naver.mail.application;

import com.bgpark.notification.domain.naver.NaverClient;
import com.bgpark.notification.domain.naver.cloud.NaverCloudClient;
import com.bgpark.notification.domain.naver.cloud.NaverCloudProperty;
import com.bgpark.notification.domain.naver.mail.infrastructure.NaverEmailProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverEmailSender {

    private final NaverCloudClient cloudClient;
    private final NaverCloudProperty cloudProperty;
    private final NaverEmailProperty emailProperty;
    private final NaverClient naverEmailClient;

    public void send(String body) {
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        String signaturePath = emailProperty.getPath();
        String url = emailProperty.getUrl() + signaturePath;
        String accessKey = cloudProperty.getAccessKey();
        String secretKey = cloudProperty.getSecretKey();
        String signature = cloudClient.createSignature(signaturePath, HttpMethod.POST.name(), timestamp, accessKey, secretKey);
        naverEmailClient.send(timestamp, url, accessKey, signature, body);
    }
}
