package com.bgpark.notification.domain.naver;

import com.bgpark.notification.domain.naver.cloud.NaverCloudClient;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NaverSenderTest {

    @Autowired
    private NaverSender naverSender;
    @Autowired
    private NaverCloudClient cloudClient;

    @Test
    void send() {
        naverSender.send();
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
}
