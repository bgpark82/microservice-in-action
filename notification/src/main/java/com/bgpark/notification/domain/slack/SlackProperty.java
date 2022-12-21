package com.bgpark.notification.domain.slack;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("slack.chat")
public class SlackProperty {

    private String token;
    private String url;

}
