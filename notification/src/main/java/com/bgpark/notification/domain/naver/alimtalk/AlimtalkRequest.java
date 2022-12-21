package com.bgpark.notification.domain.naver.alimtalk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlimtalkRequest {

    private String plusFriendId;
    private String templateCode;
    private List<Message> messages;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String to;
        private String content;
    }
}
