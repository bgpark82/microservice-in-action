package com.bgpark.notification.domain.naver.sms;

import com.bgpark.notification.domain.naver.NaverClient;
import com.bgpark.notification.domain.naver.NaverSignatureUtils;
import com.bgpark.notification.domain.naver.cloud.NaverCloudProperty;
import com.bgpark.notification.util.CustomObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverSender {

    private final NaverClient naverSmsClient;
    private final NaverCloudProperty cloudProperty;
    private final NaverSmsProperty smsProperty;
    private final CustomObjectMapper mapper;

    public void send(String body) {
        final String timestamp = String.valueOf(Instant.now().toEpochMilli());
        final String signaturePath = smsProperty.getSignaturePath();
        final String url = cloudProperty.getRequestUrl(signaturePath);
        final String accessKey = cloudProperty.getAccessKey();
        final String secretKey = cloudProperty.getSecretKey();
        final String signature = NaverSignatureUtils.createSignature(signaturePath, HttpMethod.POST.name(), timestamp, accessKey, secretKey);

        naverSmsClient.send(url, timestamp, accessKey, signature, body);
    }

    public void send(NaverSmsRequest request) {
        String body = mapper.writeValueAsString(request);
        send(body);
    }
}
