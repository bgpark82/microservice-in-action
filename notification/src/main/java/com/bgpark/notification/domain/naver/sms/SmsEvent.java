package com.bgpark.notification.domain.naver.sms;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SmsEvent {

    String SMS_EVENT_IN = "sms_event_in";
    String SMS_EVENT_OUT = "sms_event_out";

    @Input(SMS_EVENT_IN)
    SubscribableChannel smsInput();

    @Output(SMS_EVENT_OUT)
    MessageChannel smsOutput();
}
