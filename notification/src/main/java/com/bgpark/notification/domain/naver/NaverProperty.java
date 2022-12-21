package com.bgpark.notification.domain.naver;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("sms.naver")
public class NaverProperty {

    private String url;
    private String path;
    private String serviceId;
    private String sender;
    private String apiKey;
    private String apiSecret;
    private String timestampHeader;
    private String apiKeyHeader;
    private String signatureHeader;
}
