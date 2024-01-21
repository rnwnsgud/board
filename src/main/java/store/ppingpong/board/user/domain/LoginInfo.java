package store.ppingpong.board.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import store.ppingpong.board.common.handler.exception.EmailNotSupportException;
import store.ppingpong.board.user.dto.UserCreate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    public static LoginInfo of(UserCreate userCreate, PasswordEncoder passwordEncoder) {
        LoginType loginType = extractDomain(userCreate.getEmail());

        return LoginInfo.builder()
                .loginId(userCreate.getLoginId())
                .loginType(loginType)
                .encodePassword(passwordEncoder.encode(userCreate.getRawPassword()))
                .build();

    }

    private static LoginType extractDomain(String email) {
        String[] parts = email.split("@");
        String domain = parts[1];
        if ("naver.com".equals(domain)) {
            return LoginType.NAVER;
        } else if ("google.com".equals(domain)) {
            return LoginType.GOOGLE;
        } else throw new EmailNotSupportException("지원하지 않는 이메일 형식입니다.");
    }


}
