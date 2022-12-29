package com.bgpark.notification.domain.slack;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class SlackRequest {

    private String channel;

    private String text;

    public SlackRequest(String channel, String text) {
        this.channel = channel;
        this.text = text;
    }
}
