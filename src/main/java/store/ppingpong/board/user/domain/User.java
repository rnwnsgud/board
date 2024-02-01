package store.ppingpong.board.user.domain;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.service.port.ClockHolder;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class User {

    private final Long id;
    private final LoginInfo loginInfo;
    private final UserInfo userInfo;
    private UserStatus userStatus;
    private final Long createdAt;
    private final Long lastLoginAt;

    @Builder
    private User(Long id, LoginInfo loginInfo, UserInfo userInfo, UserStatus userStatus, Long createdAt, Long lastLoginAt) {
        this.id = id;
        this.loginInfo = loginInfo;
        this.userInfo = userInfo;
        this.userStatus = userStatus;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    public static User of(LoginInfo loginInfo, UserInfo userInfo, ClockHolder clockHolder) {

        return User.builder()
                .loginInfo(loginInfo)
                .userInfo(userInfo)
                .userStatus(UserStatus.PENDING)
                .createdAt(clockHolder.mills())
                .build();
    }

    public static User valueOf(long id, String role) {

        UserEnum userEnum = UserEnum.USER;

        UserEnum[] userEnums = {UserEnum.USER, UserEnum.ASSISTANT, UserEnum.MANAGER, UserEnum.ADMIN};

        for (UserEnum tmp : userEnums) {
            if (tmp.name().equals(role)) userEnum = tmp;
        }

        UserInfo userInfo = UserInfo.builder()
                .userEnum(userEnum)
                .build();

        return User.builder()
                .id(id)
                .userInfo(userInfo)
                .build();
    }

    public void verified() {
        this.userStatus = UserStatus.ACTIVE;
    }

    public User login(ClockHolder clockHolder) {
        return User.builder()
                .id(id)
                .loginInfo(loginInfo)
                .userInfo(userInfo)
                .userStatus(userStatus)
                .createdAt(createdAt)
                .lastLoginAt(clockHolder.mills())
                .build();

    }




}
