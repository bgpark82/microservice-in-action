package com.bgpark.notification.domain.slack;

import com.bgpark.notification.util.CustomObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackSender {

    private final SlackProperty property;
    private final SenderClient slackSenderClient;
    private final CustomObjectMapper mapper;

    public void send(SlackRequest request) {
        String body = mapper.writeValueAsString(request);
        slackSenderClient.send(body, property.getUrl(), property.getToken());
    }
}
