package com.bgpark.notification.domain.slack;

import com.bgpark.notification.util.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class SlackSenderAdapter implements SenderAdapter {

    @Value("${slack.chat.token}")
    private String SLACK_TOKEN;

    @Value("${slack.chat.url}")
    private String SLACK_CHAT_POST_URL;

    public <T> void send(T body) {
        verifyToken();

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(SLACK_CHAT_POST_URL);
            request.addHeader(HttpHeaders.AUTHORIZATION, Constant.BEARER_TOKEN_PREFIX + SLACK_TOKEN);
            request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.setEntity(new StringEntity(convertToString(body)));
            CloseableHttpResponse response = client.execute(request);
            log.info("request={}, response={}", request, EntityUtils.toString(response.getEntity()));

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("slack notification send fail");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void verifyToken() {
        if (!SLACK_TOKEN.startsWith(Constant.SLACK_BOT_TOKEN_PREFIX)) {
            log.error("slack token={}", SLACK_TOKEN);
            throw new RuntimeException("invalid slack token");
        }
    }

    private <T> String convertToString(T body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(body);
    }
}
