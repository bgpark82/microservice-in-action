package com.bgpark.notification.domain.kakao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("alimtalk.naver")
public class AlimktalkProperty {

    private String url;
    private String path;
    private String serviceId;
    private String apiKey;
    private String apiSecret;
    private String timestampHeader;
    private String apiKeyHeader;
    private String signatureHeader;
}
