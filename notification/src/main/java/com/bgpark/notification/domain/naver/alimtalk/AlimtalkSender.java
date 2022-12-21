package com.bgpark.notification.domain.naver.alimtalk;

import com.bgpark.notification.domain.naver.NaverClient;
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
public class AlimtalkSender {

    private final NaverClient naverAlimtalkClient;
    private final NaverCloudClient cloudClient;
    private final NaverCloudProperty cloudProperty;
    private final AlimktalkProperty alimktalkProperty;

    public void send(String body) {
        final String timestamp = String.valueOf(Instant.now().toEpochMilli());
        final String signaturePath = alimktalkProperty.getSignaturePath();
        final String url = cloudProperty.getRequestUrl(signaturePath);
        final String accessKey = cloudProperty.getAccessKey();
        final String secretKey = cloudProperty.getSecretKey();
        final String signature = cloudClient.createSignature(signaturePath, HttpMethod.POST.name(), timestamp, accessKey, secretKey);

        naverAlimtalkClient.send(url, timestamp, accessKey, signature, body);
    }
}
