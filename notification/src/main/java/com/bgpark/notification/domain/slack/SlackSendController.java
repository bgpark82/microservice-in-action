package com.bgpark.notification.domain.slack;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SlackSendController {

    private final SlackSender slackSender;

    @PostMapping("/slack")
    public ResponseEntity create(@RequestBody SlackRequest request) {
        slackSender.send(request);
        return ResponseEntity.ok().build();
    }
}
