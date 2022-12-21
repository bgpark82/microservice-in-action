package com.bgpark.notification.domain.slack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SlackSenderTest {

    @Autowired
    private SlackSender slackSender;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void send() throws JsonProcessingException {
        slackSender.send(createBody(new SlackRequest("C04F5A5SPE2", "hi")));
    }

    private String createBody(SlackRequest request) throws JsonProcessingException {
        return mapper.writeValueAsString(request);
    }
}
