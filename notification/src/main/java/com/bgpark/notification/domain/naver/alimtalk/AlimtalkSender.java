package com.bgpark.notification.domain.naver.alimtalk;

import com.bgpark.notification.domain.naver.NaverClient;
import com.bgpark.notification.domain.naver.cloud.NaverCloudClient;
import com.bgpark.notification.domain.naver.cloud.NaverCloudProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlimtalkSender {

    private final NaverClient naverAlimtalkClient;
    private final NaverCloudClient cloudClient;
    private final NaverCloudProperty cloudProperty;
    private final AlimktalkProperty alimktalkProperty;

    public void send() {
        final String timestamp = String.valueOf(Instant.now().toEpochMilli());
        final String signaturePath = alimktalkProperty.getSignaturePath();
        final String url = cloudProperty.getRequestUrl(signaturePath);
        final String accessKey = cloudProperty.getAccessKey();
        final String secretKey = cloudProperty.getSecretKey();
        final String signature = cloudClient.createSignature(signaturePath, HttpMethod.POST.name(), timestamp, accessKey, secretKey);

        naverAlimtalkClient.send(url, timestamp, accessKey, signature, createBody());
    }

    private String createBody() {
        try {
            return new ObjectMapper().writeValueAsString(new KakaoAlimtalkRequest());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    class KakaoAlimtalkRequest {
        private String plusFriendId = "@베이비페이스고객센터";
        private String templateCode = "notiAffi02";
        private List<Message> messages = Arrays.asList(new Message());

        @Getter
        class Message {

            private String to = "01045808682";
            private String content = "안녕하세요, 베이비페이스 입니다.\n" +
                    "\n" +
                    "고객님께서 전송하신 상품권으로 인해,\n" +
                    "현재 남아있는 베이비페이스 상품권 잔여 개수는 [1개] 입니다.\n" +
                    "\n" +
                    "기타 문의가 있으신 경우 베이비페이스 고객센터로 문의주세요. 감사합니다.";
        }
    }
}
