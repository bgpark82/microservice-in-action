package com.bgpark.notification.domain.naver.alimtalk;

import com.bgpark.notification.domain.naver.cloud.NaverCloudClient;
import com.bgpark.notification.domain.naver.cloud.NaverCloudConstant;
import com.bgpark.notification.domain.naver.cloud.NaverCloudProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlimtalkSender {

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

        send(url, timestamp, accessKey, signature);
    }

    private void send(String url, String timestamp, String accessKey, String signature)  {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.addHeader(NaverCloudConstant.X_NCP_APIGW_TIMESTAMP, timestamp);
            request.addHeader(NaverCloudConstant.X_NCP_IAM_ACCESS_KEY, accessKey);
            request.addHeader(NaverCloudConstant.X_NCP_APIGW_SIGNATURE_V2, signature);
            request.setEntity(new StringEntity(createBody(), StandardCharsets.UTF_8));
            CloseableHttpResponse response = client.execute(request);
            log.info("response={}", EntityUtils.toString(response.getEntity()));

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_ACCEPTED) {
                throw new RuntimeException("kakao alimtalk send fail");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createBody() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(new KakaoAlimtalkRequest());
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
