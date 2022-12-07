package com.bgpark.user.domain.social;

import com.bgpark.user.domain.social.kakao.KakaoProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KakaoProfileTest {

    @Test
    void deserialize() throws JsonProcessingException {
        String request = "{\n" +
                "            \"connected_at\": \"2022-12-02T02:05:21Z\",\n" +
                "            \"id\": 2553589492,\n" +
                "            \"kakao_account\": {\n" +
                "                \"email\": \"popi7302@naver.com\",\n" +
                "                \"email_needs_agreement\": false,\n" +
                "                \"has_email\": true,\n" +
                "                \"has_phone_number\": true,\n" +
                "                \"is_email_valid\": true,\n" +
                "                \"is_email_verified\": true,\n" +
                "                \"name\": \"박병길\",\n" +
                "                \"name_needs_agreement\": false,\n" +
                "                \"phone_number\": \"+82 10-4580-8682\",\n" +
                "                \"phone_number_needs_agreement\": false\n" +
                "             }\n" +
                "        }";
        ObjectMapper mapper = new ObjectMapper();
        KakaoProfile kakaoProfile = mapper.readValue(request, KakaoProfile.class);
        System.out.println(kakaoProfile);

        assertThat(kakaoProfile.getConnectedAt()).isEqualTo("2022-12-02T02:05:21Z");
        assertThat(kakaoProfile.getSynchedAt()).isNull();
        assertThat(kakaoProfile.getKakaoAccount().getEmail()).isEqualTo("popi7302@naver.com");
        assertThat(kakaoProfile.getKakaoAccount().getName()).isEqualTo("박병길");
        assertThat(kakaoProfile.getKakaoAccount().isEmailNeedsAgreement()).isEqualTo(false);
        assertThat(kakaoProfile.getKakaoAccount().isHasEmail()).isEqualTo(true);
        assertThat(kakaoProfile.getKakaoAccount().isHasPhoneNumber()).isEqualTo(true);
        assertThat(kakaoProfile.getKakaoAccount().isIsEmailValid()).isEqualTo(true);
        assertThat(kakaoProfile.getKakaoAccount().isIsEmailVerified()).isEqualTo(true);
        assertThat(kakaoProfile.getKakaoAccount().isNameNeedsAgreement()).isEqualTo(false);
        assertThat(kakaoProfile.getKakaoAccount().getPhoneNumber()).isEqualTo("+82 10-4580-8682");
        assertThat(kakaoProfile.getKakaoAccount().isPhoneNumberNeedsAgreement()).isEqualTo(false);
    }
}
