package store.ppingpong.board.forum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ForumUpdate {
    @Size(max = 200)
    private final String introduction;

    @Builder
    public ForumUpdate(@JsonProperty("introduction") String introduction) {
        this.introduction = introduction;
    }
}
