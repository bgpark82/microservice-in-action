package com.bgpark.notification.domain.slack;

public interface SenderClient {

    void send(String body, String url, String token);
}
