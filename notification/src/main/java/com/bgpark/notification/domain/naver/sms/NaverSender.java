package com.bgpark.notification.domain.naver.sms;

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
public class NaverSender {

    private final NaverClient naverSmsClient;
    private final NaverCloudProperty cloudProperty;
    private final NaverCloudClient cloudClient;
    private final NaverSmsProperty smsProperty;

    public void send() {
        final String timestamp = String.valueOf(Instant.now().toEpochMilli());
        final String signaturePath = smsProperty.getSignaturePath();
        final String url = cloudProperty.getRequestUrl(signaturePath);
        final String accessKey = cloudProperty.getAccessKey();
        final String secretKey = cloudProperty.getSecretKey();
        final String signature = cloudClient.createSignature(signaturePath, HttpMethod.POST.name(), timestamp, accessKey, secretKey);

        naverSmsClient.send(url, timestamp, accessKey, signature, createBody());
    }

    private String createBody() {
        return "{\n" +
                "    \"type\":\"SMS\",\n" +
                "    \"contentType\":\"COMM\",\n" +
                "    \"countryCode\":\"82\",\n" +
                "    \"from\":\"07043046482\",\n" +
                "    \"subject\":\"title\",\n" +
                "    \"content\":\"hello\",\n" +
                "    \"messages\":[\n" +
                "        {\n" +
                "            \"to\":\"01045808682\",\n" +
                "            \"subject\":\"sub title\",\n" +
                "            \"content\":\"sub content\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }


}
