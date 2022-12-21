package com.bgpark.notification.domain.kakao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlimtalkSenderTest {

    @Autowired
    private AlimtalkSender alimtalkSender;

    @Test
    void send() {
        alimtalkSender.send();
    }
}
