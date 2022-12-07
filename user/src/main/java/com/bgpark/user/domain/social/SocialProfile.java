package com.bgpark.user.domain.social;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialProfile {
    // "소셜 서비스에서 제공하는 식별 키. WeChat의 경우는 openId.")
    private String uniqueId;

    // "소셜 서비스에서 제공하는 이메일.")
    private String email;

    // "소셜 서비스에서 제공하는 이름. WeChat의 경우는 顾客 지정.")
    private String name;

    // "소셜 서비스에서 제공하는 accessToken. WeChat의 경우는 sessionKey")
    private String token;

    public SocialProfile createSocialProfile(String uniqueId, String name, String email, String token) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.email = email;
        this.token = token;
        return this;
    }
}
