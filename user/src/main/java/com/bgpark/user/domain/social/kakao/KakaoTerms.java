package com.bgpark.user.domain.social.kakao;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoTerms {

    private Long userId;
    private List<AllowedServiceTerms> allowedServiceTerms = new ArrayList<>();

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class AllowedServiceTerms {
        private String tag;
        private ZonedDateTime agreedAt;
    }
}
