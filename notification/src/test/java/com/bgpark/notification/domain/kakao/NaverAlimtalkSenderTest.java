package com.bgpark.notification.domain.kakao;

import com.bgpark.notification.domain.naver.alimtalk.NaverAlimtalkRequest;
import com.bgpark.notification.domain.naver.alimtalk.NaverAlimtalkSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class NaverAlimtalkSenderTest {

    @Autowired
    private NaverAlimtalkSender naverAlimtalkSender;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void send() throws JsonProcessingException {
        naverAlimtalkSender.send(createBody());
    }

    private String createBody() throws JsonProcessingException {
        NaverAlimtalkRequest request = NaverAlimtalkRequest.builder()
                .plusFriendId("@베이비페이스고객센터")
                .templateCode("notiAffi02")
                .messages(Arrays.asList(
                        NaverAlimtalkRequest.Message.builder()
                                .to("01045808682")
                                .content("안녕하세요, 베이비페이스 입니다.\n" +
                                        "\n" +
                                        "고객님께서 전송하신 상품권으로 인해,\n" +
                                        "현재 남아있는 베이비페이스 상품권 잔여 개수는 [1개] 입니다.\n" +
                                        "\n" +
                                        "기타 문의가 있으신 경우 베이비페이스 고객센터로 문의주세요. 감사합니다.")
                                .build()
                )).build();
        String body = mapper.writeValueAsString(request);
        System.out.println(body);
        return body;
    }

}
