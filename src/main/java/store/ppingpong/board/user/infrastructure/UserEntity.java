package store.ppingpong.board.user.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.ppingpong.board.user.domain.LoginInfo;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.domain.UserInfo;
import store.ppingpong.board.user.domain.UserStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tb")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private LoginInfo loginInfo;
    @Embedded
    private UserInfo userInfo;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private Long createdAt;
    private Long lastLoginAt;

    @Builder
    private UserEntity(Long id, LoginInfo loginInfo, UserInfo userInfo, UserStatus userStatus, Long createdAt, Long lastLoginAt) {
        this.id = id;
        this.loginInfo = loginInfo;
        this.userInfo = userInfo;
        this.userStatus = userStatus;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .userInfo(user.getUserInfo())
                .loginInfo(user.getLoginInfo())
                .userStatus(user.getUserStatus())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    public User toModel() {
        return User.builder()
                .id(id)
                .userInfo(userInfo)
                .loginInfo(loginInfo)
                .userInfo(userInfo)
                .createdAt(createdAt)
                .lastLoginAt(lastLoginAt)
                .build();
    }
}
