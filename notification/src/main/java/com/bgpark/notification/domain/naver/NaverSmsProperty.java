package com.bgpark.notification.domain.naver;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("naver.sms")
public class NaverSmsProperty {

    private String path;
    private String serviceId;
    private String sender;

    public String getSignaturePath() {
        return path + "/services/" + serviceId + "/messages";
    }
}
