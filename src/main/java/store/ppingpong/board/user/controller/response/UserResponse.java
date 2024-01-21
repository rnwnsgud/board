package store.ppingpong.board.user.controller.response;

import lombok.Builder;
import lombok.Getter;
import store.ppingpong.board.user.domain.User;
import store.ppingpong.board.user.domain.UserStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {

    private long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private LocalDateTime createdAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getUserInfo().getEmail())
                .nickname(user.getUserInfo().getNickname())
                .status(UserStatus.PENDING)
                .build();

    }
}
