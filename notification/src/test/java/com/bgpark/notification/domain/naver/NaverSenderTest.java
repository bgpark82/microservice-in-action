package com.bgpark.notification.domain.naver;

import com.bgpark.notification.domain.naver.cloud.NaverCloudClient;
import com.bgpark.notification.domain.naver.sms.NaverSender;
import com.bgpark.notification.domain.naver.sms.NaverSmsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NaverSenderTest {

    @Autowired
    private NaverSender naverSender;
    @Autowired
    private NaverCloudClient cloudClient;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void send() throws JsonProcessingException {
        naverSender.send(createBody());
    }

    @Test
    void makeSignature() {
        String TIMESTAMP = String.valueOf(Instant.now().toEpochMilli());
        String ACCESS_KEY = "h3wgv38IOOhcXNlrfljT";
        String SECRET_KEY = "AbivoGPePxmX0CySNvwmy84ov8N6moBeFSj6wyyw";
        String SERVICE_ID = "ncp:sms:kr:252880318896:bf_user_verify";
        String path = "/services/"+ SERVICE_ID +"/messages";
        String signature = cloudClient.createSignature(path, "POST", TIMESTAMP, ACCESS_KEY, SECRET_KEY);

        assertThat(signature).isBase64();
    }

    private String createBody() throws JsonProcessingException {
        NaverSmsRequest request = NaverSmsRequest.builder()
                .type(NaverSmsRequest.SmsType.SMS)
                .contentType(NaverSmsRequest.ContentType.COMM)
                .countryCode(NaverSmsRequest.CountryCode.KOREA.getCode())
                .from("07043046482")
                .subject("title")
                .content("hello")
                .messages(Arrays.asList(
                        NaverSmsRequest.Message.builder()
                                .to("01045808682")
                                .subject("[베이비페이스]")
                                .content("안녕하세요!")
                                .build()
                )).build();
        return mapper.writeValueAsString(request);
    }
}
