package com.bgpark.notification.domain.naver.alimtalk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverAlimtalkListener {

    private final NaverAlimtalkSender alimtalkSender;

    @StreamListener(AlimtalkEvent.ALIMTALK_EVENT_IN)
    public void process(NaverAlimtalkRequest request) {
        log.info("request={}", request);
        alimtalkSender.send(request);
    }

}
