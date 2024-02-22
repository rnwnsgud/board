package store.ppingpong.board.user.dto;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.user.domain.LoginType;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.domain.UserStatus;

@Getter
@Builder
public class UserResponse {

    private long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private LoginType loginType;
    private Long createdAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getUserInfo().getEmail())
                .nickname(user.getUserInfo().getNickname())
                .loginType(user.getLoginInfo().getLoginType())
                .status(UserStatus.PENDING)
                .createdAt(user.getCreatedAt())
                .build();

    }
}
