package com.bgpark.notification.domain.naver.mail.domain;

import com.bgpark.notification.domain.naver.mail.NaverEmailSender;
import com.bgpark.notification.domain.naver.mail.NaverEmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class NaverEmailSenderTest {

    @Autowired
    private NaverEmailSender naverEmailSender;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void send() throws JsonProcessingException {
        naverEmailSender.send(createBody());
    }

    private String createBody() throws JsonProcessingException {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("BEFORE_GRADE", "SILVER");
        paramMap.put("AFTER_GRADE", "GOLD");

        NaverEmailRequest request = NaverEmailRequest.builder()
                .senderAddress("no_reply@company.com")
                .title("병길님 반갑습니다.")
                .body("귀하의 등급이 ${BEFORE_GRADE}에서 ${AFTER_GRADE}로 변경되었습니다.")
                .individual(true)
                .advertising(false)
                .recipients(Arrays.asList(
                        NaverEmailRequest.NaverEmailRecipient.builder()
                                .address("popi7302@naver.com")
                                .name("박병길")
                                .type("R")
                                .parameters(paramMap)
                                .build()
                )).build();
        String body = mapper.writeValueAsString(request);
        System.out.println(body);
        return body;
    }
}
