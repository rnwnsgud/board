package store.ppingpong.board.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginReq {
    private final String loginId;
    private final String rawPassword;

    @Builder
    public UserLoginReq(@JsonProperty("loginId") String loginId,
                        @JsonProperty("rawPassword") String rawPassword) {
        this.loginId = loginId;
        this.rawPassword = rawPassword;
    }
}
