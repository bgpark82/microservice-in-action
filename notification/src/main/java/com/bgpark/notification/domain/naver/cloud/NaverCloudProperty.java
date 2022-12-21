package com.bgpark.notification.domain.naver.cloud;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("naver.cloud")
public class NaverCloudProperty {

    private String url;
    private String accessKey;
    private String secretKey;
}


