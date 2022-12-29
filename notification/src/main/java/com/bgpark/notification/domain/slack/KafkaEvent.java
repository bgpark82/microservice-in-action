package com.bgpark.notification.domain.slack;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public interface KafkaEvent {

    static <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }
}
