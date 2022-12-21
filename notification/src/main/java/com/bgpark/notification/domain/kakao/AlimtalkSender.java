package com.bgpark.notification.domain.kakao;

import com.bgpark.notification.util.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlimtalkSender {

    private static final String X_NCP_APIGW_TIMESTAMP = "x-ncp-apigw-timestamp";
    private static final String X_NCP_IAM_ACCESS_KEY = "x-ncp-iam-access-key";
    private static final String X_NCP_APIGW_SIGNATURE_V2 = "x-ncp-apigw-signature-v2";

    private final AlimktalkProperty property;

    public void send() {
        String accessKey = property.getApiKey();
        String secretKey = property.getApiSecret();
        String signaturePath = property.getPath() + "/services/" + property.getServiceId() + "/messages";
        String url = property.getUrl() + signaturePath;
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        String signature = createSignature(signaturePath, HttpMethod.POST.name(), timestamp, accessKey, secretKey);

        send(url, timestamp, accessKey, signature);
    }

    private void send(String url, String timestamp, String accessKey, String signature)  {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.addHeader(X_NCP_APIGW_TIMESTAMP, timestamp);
            request.addHeader(X_NCP_IAM_ACCESS_KEY, accessKey);
            request.addHeader(X_NCP_APIGW_SIGNATURE_V2, signature);
            request.setEntity(new StringEntity(createBody(), StandardCharsets.UTF_8));
            CloseableHttpResponse response = client.execute(request);
            log.info("request={}", request);
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


    public String createSignature(String path, String method, String timestamp, String accessKey, String secretKey) {
        String message = createSignatureMessage(path, method, timestamp, accessKey);
        byte[] rawHmac = createRawHmac(secretKey, message);
        return Base64.encodeBase64String(rawHmac);
    }

    private byte[] createRawHmac(String secretKey, String message) {
        try {
            Mac mac = Mac.getInstance(Constant.HMAC_SHA256);
            mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Constant.HMAC_SHA256));
            return mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private String createSignatureMessage(String path, String method, String timestamp, String accessKey) {
        return new StringBuilder()
                .append(method)
                .append(Constant.SPACE)
                .append(path)
                .append(Constant.NEW_LINE)
                .append(timestamp)
                .append(Constant.NEW_LINE)
                .append(accessKey)
                .toString();
    }
}

/**
 * POST https://sens.apigw.ntruss.com/alimtalk/v2/services/{serviceId}/messages
 *
 * Content-Type: application/json; charset=utf-8
 * x-ncp-apigw-timestamp: {Timestamp}
 * x-ncp-iam-access-key: {Sub Account Access Key}
 * x-ncp-apigw-signature-v2: {API Gateway Signature}
 *
 *
 *     url: https://sens.apigw.ntruss.com
 *     path: /sms/v2
 *     service-id: ncp:sms:kr:252880318896:bf_user_verify
 *     sender: 07043046482
 *     api-key: h3wgv38IOOhcXNlrfljT
 *     api-secret: AbivoGPePxmX0CySNvwmy84ov8N6moBeFSj6wyyw
 *     timestamp-header: x-ncp-apigw-timestamp
 *     api-key-header: x-ncp-iam-access-key
 *     signature-header: x-ncp-apigw-signature-v2
 */
