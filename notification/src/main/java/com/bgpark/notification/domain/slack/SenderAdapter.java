package com.bgpark.notification.domain.slack;

public interface SenderAdapter {

    void send(String body, String url, String token);
}
