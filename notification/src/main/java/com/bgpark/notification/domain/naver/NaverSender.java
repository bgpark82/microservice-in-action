package com.bgpark.notification.domain.naver;

import com.bgpark.notification.util.Constant;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverSender {
    private static final String X_NCP_APIGW_TIMESTAMP = "x-ncp-apigw-timestamp";
    private static final String X_NCP_IAM_ACCESS_KEY = "x-ncp-iam-access-key";
    private static final String X_NCP_APIGW_SIGNATURE_V2 = "x-ncp-apigw-signature-v2";

    private final NaverProperty property;

    public void send() {
        final String signaturePath = property.getPath() + "/services/" + property.getServiceId() + "/messages";
        final String smsUrl = property.getUrl() + signaturePath;
        final String timestamp = String.valueOf(Instant.now().toEpochMilli());
        final String accessKey = property.getApiKey();
        final String secretKey = property.getApiSecret();
        final String signature = createSignature(signaturePath, HttpMethod.POST.name(), timestamp, accessKey, secretKey);

        send(smsUrl, timestamp, accessKey, signature);
    }

    private void send(String url, String timestamp, String accessKey, String signature) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.addHeader(X_NCP_APIGW_TIMESTAMP, timestamp);
            request.addHeader(X_NCP_IAM_ACCESS_KEY, accessKey);
            request.addHeader(X_NCP_APIGW_SIGNATURE_V2, signature);
            request.setEntity(new StringEntity(createBody()));
            CloseableHttpResponse response = client.execute(request);

            log.info("response={}", response);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_ACCEPTED) {
                throw new RuntimeException("naver sms send fail");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createBody() {
        return "{\n" +
                "    \"type\":\"SMS\",\n" +
                "    \"contentType\":\"COMM\",\n" +
                "    \"countryCode\":\"82\",\n" +
                "    \"from\":\"07043046482\",\n" +
                "    \"subject\":\"title\",\n" +
                "    \"content\":\"hello\",\n" +
                "    \"messages\":[\n" +
                "        {\n" +
                "            \"to\":\"01045808682\",\n" +
                "            \"subject\":\"sub title\",\n" +
                "            \"content\":\"sub content\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
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
