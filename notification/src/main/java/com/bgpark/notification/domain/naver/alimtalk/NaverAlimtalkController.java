package com.bgpark.notification.domain.naver.alimtalk;

import com.bgpark.notification.domain.slack.KafkaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NaverAlimtalkController {

    private final AlimtalkEvent alimtalkEvent;

    @PostMapping("/alimtalk")
    public ResponseEntity create(@RequestBody NaverAlimtalkRequest request) {
        alimtalkEvent.alimtalkOutput().send(KafkaEvent.message(request));
        return ResponseEntity.ok().build();
    }
}
