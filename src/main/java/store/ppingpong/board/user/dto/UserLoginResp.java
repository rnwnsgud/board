package store.ppingpong.board.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
public class UserLoginResp {
    private final long id;
    private final String loginId;
    private final String createdAt;
    private final String accessToken;

    @Builder
    public UserLoginResp(@JsonProperty("id")long id,
                         @JsonProperty("loginId") String loginId,
                         @JsonProperty("createdAt") String createdAt,
                         @JsonProperty("accessToken") String accessToken) {
        this.id = id;
        this.loginId = loginId;
        this.createdAt = createdAt;
        this.accessToken = accessToken;
    }
}
