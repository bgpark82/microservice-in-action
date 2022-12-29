package com.bgpark.notification.domain.naver.mail;

import com.bgpark.notification.domain.slack.KafkaEvent;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EmailEvent extends KafkaEvent {

    String EMAIL_EVENT_IN = "email_event_in";
    String EMAIL_EVENT_OUT = "email_event_out";

    @Input(EMAIL_EVENT_IN)
    SubscribableChannel emailInput();

    @Output(EMAIL_EVENT_OUT)
    MessageChannel emailOutput();
}
