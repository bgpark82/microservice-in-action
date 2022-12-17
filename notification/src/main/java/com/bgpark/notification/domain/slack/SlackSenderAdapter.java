package com.bgpark.notification.domain.slack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class SlackSenderAdapter {

    public <T> void send(T body) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("https://slack.com/api/chat.postMessage");
            request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + "xoxb-24377016762647-4507457471415-WfDeiAWSkfH0NAF9jEH1FIFh");
            request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.setEntity(new StringEntity(convertToString(body)));
            CloseableHttpResponse response = client.execute(request);
            log.info("response={}", response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> String convertToString(T body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(body);
    }

}
