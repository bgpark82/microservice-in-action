package com.bgpark.notification.domain.naver;

import com.bgpark.notification.domain.naver.cloud.NaverCloudClient;
import com.bgpark.notification.domain.naver.cloud.NaverCloudProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverSender {
    private static final String X_NCP_APIGW_TIMESTAMP = "x-ncp-apigw-timestamp";
    private static final String X_NCP_IAM_ACCESS_KEY = "x-ncp-iam-access-key";
    private static final String X_NCP_APIGW_SIGNATURE_V2 = "x-ncp-apigw-signature-v2";

    private final NaverCloudProperty cloudProperty;
    private final NaverSmsProperty property;
    private final NaverCloudClient cloudClient;

    public void send() {
        final String timestamp = String.valueOf(Instant.now().toEpochMilli());
        final String signaturePath = property.getPath() + "/services/" + property.getServiceId() + "/messages";
        final String url = cloudProperty.getUrl() + signaturePath;
        final String accessKey = cloudProperty.getAccessKey();
        final String secretKey = cloudProperty.getSecretKey();
        final String signature = cloudClient.createSignature(signaturePath, HttpMethod.POST.name(), timestamp, accessKey, secretKey);

        send(url, timestamp, accessKey, signature);
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


}
