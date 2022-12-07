package com.bgpark.user.domain.social.naver;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public class NaverProfile {

    @ToString
    @Getter
    @Setter
    @NoArgsConstructor
    public class Profile {
        // "식별 키."
        private String id;

        // "기본적으로 네이버 내정보에 등록되어 있는 '기본 이메일' 즉 네이버ID@naver.com 값이나, 사용자가 다른 외부메일로 변경했을 경우는 변경된 이메일 주소로 됩니다.")
        private String email;

        // "사용자 이름."
        private String name;
    }
    @JsonAlias("resultcode")
    private String resultCode;

    private String message;

    private Profile response;
}
