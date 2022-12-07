package com.bgpark.user.domain.social.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class KakaoTermsTest {

    @Test
    void terms() throws JsonProcessingException {
        String body = "{\n" +
                "  \"user_id\": 2557568919,\n" +
                "  \"allowed_service_terms\": [\n" +
                "    {\n" +
                "      \"tag\": \"marketing_agree\",\n" +
                "      \"agreed_at\": \"2022-12-01T05:27:41Z\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        KakaoTerms terms = mapper.readValue(body, KakaoTerms.class);

        assertThat(terms.getUserId()).isEqualTo(2557568919L);
        assertThat(terms.getAllowedServiceTerms().get(0).getTag()).isEqualTo("marketing_agree");
        assertThat(terms.getAllowedServiceTerms().get(0).getAgreedAt()).isEqualTo("2022-12-01T05:27:41Z");
    }
}
