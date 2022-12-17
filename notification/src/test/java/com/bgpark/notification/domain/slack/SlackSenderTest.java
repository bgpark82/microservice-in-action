package com.bgpark.notification.domain.slack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SlackSenderTest {

    @Autowired
    private SlackSender slackSender;

    @Test
    void send() {
        slackSender.send();
    }
}
