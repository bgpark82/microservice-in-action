package com.bgpark.notification.domain.slack;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SlackEvent {

    String SLACK_EVENT_IN = "slack_event_in";
    String SLACK_EVENT_OUT = "slack_event_out";

    @Input(SLACK_EVENT_IN)
    SubscribableChannel slackInput();

    @Output(SLACK_EVENT_OUT)
    MessageChannel slackOut();

    static <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }
}
