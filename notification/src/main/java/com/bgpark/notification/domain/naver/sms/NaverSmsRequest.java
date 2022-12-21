package com.bgpark.notification.domain.naver.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverSmsRequest {

    private SmsType type;
    private ContentType contentType;
    private int countryCode;
    private String from;
    private String subject;
    private String content;
    private List<Message> messages;

    public enum SmsType {
        SMS;
    }

    public enum ContentType {
        COMM;
    }

    @Getter
    public enum CountryCode {
        KOREA(82);

        private final int code;

        CountryCode(int code) {
            this.code = code;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String to;
        private String subject;
        private String content;
    }

}
