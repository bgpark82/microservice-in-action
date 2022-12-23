package com.bgpark.notification.domain.naver.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("naver.email")
public class NaverEmailProperty {

    private String url;
    private String path;
}
