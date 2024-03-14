package store.ppingpong.board.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {

    @NotBlank
    private final String loginId;
    @Size(min = 2, max = 12)
    private final String nickname;
    @Size(min = 4, max = 20)
    private final String rawPassword;
    @Email
    private final String email;

    @Builder
    public UserCreate(@JsonProperty("loginId") String loginId,
                      @JsonProperty("nickname") String nickname,
                      @JsonProperty("rawPassword") String rawPassword,
                      @JsonProperty("email") String email) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.rawPassword = rawPassword;
        this.email = email;
    }
}
