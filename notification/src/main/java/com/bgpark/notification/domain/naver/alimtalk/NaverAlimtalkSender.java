package com.bgpark.notification.domain.naver.alimtalk;

import com.bgpark.notification.domain.naver.NaverClient;
import com.bgpark.notification.domain.naver.NaverSignatureUtils;
import com.bgpark.notification.domain.naver.cloud.NaverCloudClient;
import com.bgpark.notification.domain.naver.cloud.NaverCloudProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverAlimtalkSender {

    private final NaverClient naverAlimtalkClient;
    private final NaverCloudProperty cloudProperty;
    private final NaverAlimktalkProperty naverAlimktalkProperty;

    public void send(String body) {
        final String timestamp = String.valueOf(Instant.now().toEpochMilli());
        final String signaturePath = naverAlimktalkProperty.getSignaturePath();
        final String url = cloudProperty.getRequestUrl(signaturePath);
        final String accessKey = cloudProperty.getAccessKey();
        final String secretKey = cloudProperty.getSecretKey();
        final String signature = NaverSignatureUtils.createSignature(signaturePath, HttpMethod.POST.name(), timestamp, accessKey, secretKey);

        naverAlimtalkClient.send(url, timestamp, accessKey, signature, body);
    }
}
