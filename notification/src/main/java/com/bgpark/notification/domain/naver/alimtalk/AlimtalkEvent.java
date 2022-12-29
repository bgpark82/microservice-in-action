package com.bgpark.notification.domain.naver.alimtalk;

import com.bgpark.notification.domain.slack.KafkaEvent;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface AlimtalkEvent extends KafkaEvent {

    String ALIMTALK_EVENT_IN = "alimtalk_event_in";
    String ALIMTALK_EVENT_OUT = "alimtalk_event_out";

    @Input(ALIMTALK_EVENT_IN)
    SubscribableChannel alimtalkInput();

    @Output(ALIMTALK_EVENT_OUT)
    MessageChannel alimtalkOutput();
}
