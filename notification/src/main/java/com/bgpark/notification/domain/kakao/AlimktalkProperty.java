package com.bgpark.notification.domain.kakao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("naver.alimtalk")
public class AlimktalkProperty {

    private String path;
    private String serviceId;
}
