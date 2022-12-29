package com.bgpark.notification.domain.slack;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SlackSendController {

    private final SlackEvent slackEvent;

    @PostMapping("/slack")
    public ResponseEntity create(@RequestBody SlackRequest request) {
        slackEvent.slackOut().send(SlackEvent.message(request));
        return ResponseEntity.ok().build();
    }
}
