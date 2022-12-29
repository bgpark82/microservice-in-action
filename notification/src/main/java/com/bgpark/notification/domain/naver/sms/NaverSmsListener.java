package com.bgpark.notification.domain.naver.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverSmsListener {

    private final NaverSender naverSender;

    @StreamListener(SmsEvent.SMS_EVENT_IN)
    public void input(NaverSmsRequest request) {
        log.info("request={}", request);
        naverSender.send(request);
    }
}
