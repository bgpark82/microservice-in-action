package com.bgpark.notification.domain.slack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackSender {

    private final SenderAdapter slackSenderAdapter;

    public void send(SlackRequest request) {
        slackSenderAdapter.send(request);
    }
}
