package com.bgpark.notification.domain.naver.mail.infrastructure;

import com.bgpark.notification.domain.naver.NaverClient;
import com.bgpark.notification.domain.naver.cloud.NaverCloudConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class NaverEmailClient implements NaverClient {

    @Override
    public void send(String timestamp, String url, String accessKey, String signature, String body) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.addHeader(NaverCloudConstant.X_NCP_APIGW_TIMESTAMP, timestamp);
            request.addHeader(NaverCloudConstant.X_NCP_IAM_ACCESS_KEY, accessKey);
            request.addHeader(NaverCloudConstant.X_NCP_APIGW_SIGNATURE_V2, signature);
            request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            CloseableHttpResponse response = client.execute(request);

            log.info("response={} body={}", response, EntityUtils.toString(response.getEntity()));
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
                throw new RuntimeException("naver sms send fail");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
