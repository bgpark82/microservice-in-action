package com.bgpark.notification.domain.naver.sms;

import com.bgpark.notification.domain.slack.KafkaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NaverSmsController {

    private final SmsEvent smsEvent;

    @PostMapping("/sms")
    public ResponseEntity create(@RequestBody NaverSmsRequest request) {
        smsEvent.smsOutput().send(KafkaEvent.message(request));
        return ResponseEntity.ok().build();
    }
}
