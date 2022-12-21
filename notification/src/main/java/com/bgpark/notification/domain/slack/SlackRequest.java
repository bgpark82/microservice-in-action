package com.bgpark.notification.domain.slack;

import lombok.Getter;

@Getter
public class SlackRequest {

    private String channel;

    private String text;

    public SlackRequest(String channel, String text) {
        this.channel = channel;
        this.text = text;
    }
}
