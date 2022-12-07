package com.bgpark.user.domain.social.kakao;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoProfile {

    private String id;
    private String connectedAt;
    private String synchedAt;
    private KakaoAccount kakaoAccount;

    @ToString
    static public class KakaoAccount {
        private String email;
        private String name;
        @JsonAlias("phone_number")
        private String phoneNumber;
        @JsonAlias("email_needs_agreement")
        private boolean emailNeedsAgreement;
        @JsonAlias("has_email")
        private boolean hasEmail;
        @JsonAlias("has_phone_number")
        private boolean hasPhoneNumber;
        @JsonAlias("is_email_valid")
        private boolean isEmailValid;
        @JsonAlias("is_email_verified")
        private boolean isEmailVerified;
        @JsonAlias("name_needs_agreement")
        private boolean nameNeedsAgreement;
        @JsonAlias("phone_number_needs_agreement")
        private boolean phoneNumberNeedsAgreement;

        public boolean isIsEmailValid() {
            return isEmailValid;
        }
        public boolean isIsEmailVerified() {
            return isEmailVerified;
        }

        public boolean isNameNeedsAgreement() {
            return nameNeedsAgreement;
        }

        public boolean isPhoneNumberNeedsAgreement() {
            return phoneNumberNeedsAgreement;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public boolean isEmailNeedsAgreement() {
            return emailNeedsAgreement;
        }

        public boolean isHasEmail() {
            return hasEmail;
        }

        public boolean isHasPhoneNumber() {
            return hasPhoneNumber;
        }
    }
}
