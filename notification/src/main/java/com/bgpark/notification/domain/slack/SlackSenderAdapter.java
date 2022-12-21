package com.bgpark.notification.domain.slack;

import com.bgpark.notification.util.Constant;
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

@Slf4j
@Component
public class SlackSenderAdapter implements SenderAdapter {

    public void send(String body, String url, String token) {
        verifyToken(token);

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            request.addHeader(HttpHeaders.AUTHORIZATION, Constant.BEARER_TOKEN_PREFIX + token);
            request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.setEntity(new StringEntity(body));
            CloseableHttpResponse response = client.execute(request);
            log.info("request={}, response={}", request, EntityUtils.toString(response.getEntity()));

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("slack notification send fail");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void verifyToken(String slack_token) {
        if (!slack_token.startsWith(Constant.SLACK_BOT_TOKEN_PREFIX)) {
            log.error("slack token={}", slack_token);
            throw new RuntimeException("invalid slack token");
        }
    }
}
