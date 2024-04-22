package store.ppingpong.board.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ppingpong.board.common.handler.exception.EmailNotSupportedException;
import store.ppingpong.board.user.dto.UserCreate;
import store.ppingpong.board.user.application.port.CustomPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class LoginInfo {
    private String loginId;
    @Enumerated(EnumType.STRING)
    private LoginType loginType;
    private String encodePassword;

    @Builder
    public LoginInfo(String loginId, LoginType loginType, String encodePassword) {
        this.loginId = loginId;
        this.loginType = loginType;
        this.encodePassword = encodePassword;
    }


    public static LoginInfo of(UserCreate userCreate, CustomPasswordEncoder passwordEncoder) {
        LoginType loginType = extractDomainName(userCreate.getEmail());

        return LoginInfo.builder()
                .loginId(userCreate.getLoginId())
                .loginType(loginType)
                .encodePassword(passwordEncoder.encode(userCreate.getRawPassword()))
                .build();

    }

    private static LoginType extractDomainName(String email) {
        String[] parts = email.split("@");
        String domain = parts[1];
        if ("naver.com".equals(domain)) {
            return LoginType.NAVER;
        } else if ("gmail.com".equals(domain)) {
            return LoginType.GOOGLE;
        } else throw new EmailNotSupportedException("지원하지 않는 이메일 형식입니다.");
    }


}
