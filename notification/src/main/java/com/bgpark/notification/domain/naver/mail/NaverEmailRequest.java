package com.bgpark.notification.domain.naver.mail;

import lombok.*;

import java.util.List;
import java.util.Map;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverEmailRequest {
    private String senderAddress;
    private String title;
    private String body;
    private List<NaverEmailRecipient> recipients;
    private boolean individual;
    private boolean advertising;

    @Data
    @ToString
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverEmailRecipient {
        private String address;
        private String name;
        private String type;
        private Map<String, String> parameters;
    }
}
