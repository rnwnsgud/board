package store.ppingpong.board.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.common.domain.ClockHolder;
import store.ppingpong.board.common.handler.exception.resource.ResourceNotVerifiedException;
import store.ppingpong.board.user.infrastructure.UserEntity;

@Getter
public class User {

    private final Long id;
    private final LoginInfo loginInfo;
    private final UserInfo userInfo;
    private final UserStatus userStatus;
    private final Long createdAt;
    private final Long lastLoginAt;

    @Builder(access = AccessLevel.PRIVATE)
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

    public static User from(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userInfo(userEntity.getUserInfo())
                .loginInfo(userEntity.getLoginInfo())
                .userStatus(userEntity.getUserStatus())
                .createdAt(userEntity.getCreatedAt())
                .lastLoginAt(userEntity.getLastLoginAt())
                .build();
    }

    public static User valueOf(long id, String role) {
        UserType userType = UserType.USER;
        UserType[] userTypes = {UserType.USER,  UserType.ADMIN};

        for (UserType tmp : userTypes) {
            if (tmp.name().equals(role)) userType = tmp;
        }
        UserInfo userInfo = UserInfo.builder()
                .userType(userType)
                .build();

        return User.builder()
                .id(id)
                .userInfo(userInfo)
                .build();
    }

    public User verified() {
        return User.builder()
                .id(id)
                .loginInfo(loginInfo)
                .userInfo(userInfo)
                .userStatus(UserStatus.ACTIVE)
                .createdAt(createdAt)
                .lastLoginAt(lastLoginAt)
                .build();
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

    public void isVerified() {
        if (userStatus != UserStatus.ACTIVE) throw new ResourceNotVerifiedException("인증되지 않은 유저입니다. 이메일인증을 해주세요");
    }

}
