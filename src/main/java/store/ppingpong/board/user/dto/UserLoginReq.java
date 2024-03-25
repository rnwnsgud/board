package store.ppingpong.board.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginReq {
    @Size(min = 4, max =20)
    private final String loginId;
    @Size(min = 4, max =20)
    private final String rawPassword;

    @Builder
    public UserLoginReq(@JsonProperty("loginId") String loginId,
                        @JsonProperty("rawPassword") String rawPassword) {
        this.loginId = loginId;
        this.rawPassword = rawPassword;
    }
}
