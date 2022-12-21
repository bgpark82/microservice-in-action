package com.bgpark.notification.domain.slack;

public interface SenderAdapter {

    <T> void send(T body);
}
