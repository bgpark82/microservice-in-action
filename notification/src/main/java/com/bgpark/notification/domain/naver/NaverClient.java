package com.bgpark.notification.domain.naver;

public interface NaverClient {

    void send(String url, String timestamp, String accessKey, String signature, String body);
}
