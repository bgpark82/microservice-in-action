package com.bgpark.notification.domain.naver.mail;

import com.bgpark.notification.domain.slack.KafkaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NaverEmailController {

    private final EmailEvent emailEvent;

    @PostMapping("/email")
    public ResponseEntity create(@RequestBody NaverEmailRequest request) {
        emailEvent.emailOutput().send(KafkaEvent.message(request));
        return ResponseEntity.ok().build();
    }
}
