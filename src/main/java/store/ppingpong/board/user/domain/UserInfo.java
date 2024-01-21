package store.ppingpong.board.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ppingpong.board.user.dto.UserCreate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
public class UserInfo {
    private String email;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private UserEnum userEnum;

    @Builder
    public UserInfo(String email, String nickname, UserEnum userEnum) {
        this.email = email;
        this.nickname = nickname;
        this.userEnum = userEnum;
    }

    public static UserInfo from(UserCreate userCreate) {
        return UserInfo.builder()
                .email(userCreate.getEmail())
                .nickname(userCreate.getNickname())
                .userEnum(UserEnum.USER)
                .build();
    }
}
