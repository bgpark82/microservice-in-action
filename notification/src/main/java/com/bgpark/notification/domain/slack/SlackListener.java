package com.bgpark.notification.domain.slack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackListener {

    private final SlackSender slackSender;

    @StreamListener(target = SlackEvent.SLACK_EVENT_IN)
    public void process(SlackRequest request) {
        log.info("kafka event={}", request);
        slackSender.send(request);
    }
}
