package com.bgpark.notification.domain.slack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackSender {

    private final SlackSenderAdapter slackSenderAdapter;

    public void send() {
        Map<String, String> body = createBody();
        slackSenderAdapter.send(body);
    }

    private Map<String, String> createBody() {
        Map<String, String> body = new HashMap<>();
        body.put("channel", "C04F5A5SPE2");
        body.put("text", "hello");
        return body;
    }
}
