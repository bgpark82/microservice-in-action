package com.bgpark.notification.domain.naver.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverEmailListener {

    private final NaverEmailSender emailSender;

    @StreamListener(EmailEvent.EMAIL_EVENT_IN)
    public void process(NaverEmailRequest request) {
        log.info("request={}", request);
        emailSender.send(request);
    }
}
